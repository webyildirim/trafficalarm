package com.trafficalarm.rest.mvc;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.core.model.entities.Role;
import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.services.AccountService;
import com.trafficalarm.core.services.VerificationTokenService;
import com.trafficalarm.core.services.exceptions.AccountDoesNotExistException;
import com.trafficalarm.core.services.exceptions.EntityAlreadyExistsException;
import com.trafficalarm.core.services.util.RouteGroupList;
import com.trafficalarm.rest.api.user.ApiUser;
import com.trafficalarm.rest.api.user.CreateUserRequest;
import com.trafficalarm.rest.api.user.CreateUserResponse;
import com.trafficalarm.rest.api.user.EmailVerificationRequest;
import com.trafficalarm.rest.api.user.LostPasswordRequest;
import com.trafficalarm.rest.api.user.PasswordRequest;
import com.trafficalarm.rest.api.user.UpdateUserRequest;
import com.trafficalarm.rest.exceptions.ConflictException;
import com.trafficalarm.rest.exceptions.ForbiddenException;
import com.trafficalarm.rest.exceptions.NotFoundException;
import com.trafficalarm.rest.mvc.resource.BaseResource;
import com.trafficalarm.rest.resources.AccountResource;
import com.trafficalarm.rest.resources.RouteGroupListResource;
import com.trafficalarm.rest.resources.RouteGroupResource;
import com.trafficalarm.rest.resources.asm.AccountResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteGroupListResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteGroupResourceAsm;

/**
 * Created by webyildirim on 6/28/14.
 */
@Controller
@RequestMapping("/rest/accounts")
public class AccountController extends BaseResource
{
	private AccountService accountService;
	private VerificationTokenService verificationTokenService;
	private DefaultTokenServices tokenServices;
	private PasswordEncoder passwordEncoder;
	private ClientDetailsService clientDetailsService;

	@Autowired
	public AccountController(final AccountService accountService, final VerificationTokenService verificationTokenService,
			final DefaultTokenServices defaultTokenServices, final PasswordEncoder passwordEncoder, ClientDetailsService clientDetailsService)
	{
		this.accountService = accountService;
	}

	/**
	@RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<AccountResource> getAccount(@PathVariable String accountId)
	{
		Account account = accountService.findAccount(accountId);
		if (account != null)
		{
			AccountResource res = new AccountResourceAsm().toResource(account);
			return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
		} else
		{
			return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
		}
	}*/

	@RolesAllowed({"ROLE_USER"})
    @Path("{userId}")
    @GET
	public ApiUser getUser(final @PathParam("userId") String userId, final @Context SecurityContext securityContext)
	{
		Account requestingUser = ensureUserIsAuthorized(securityContext, userId);
		return accountService.getUser(requestingUser.getId());
	}

    @RolesAllowed({"ROLE_USER"})
    @Path("{userId}")
    @PUT
	public Response updateUser(@Context SecurityContext sc, @PathParam("userId") String userId, UpdateUserRequest request)
	{
		Account requestingUser = ensureUserIsAuthorized(sc, userId);

		boolean sendVerificationToken = StringUtils.hasLength(request.getEmailAddress())
				&& !request.getEmailAddress().equals(requestingUser.getName());
		ApiUser savedUser = accountService.saveUser(userId, request);
		if (sendVerificationToken)
		{
			verificationTokenService.sendEmailVerificationToken(savedUser.getId());
		}
		return Response.ok().build();
	}

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public Response signupUser(final CreateUserRequest request, @Context SecurityContext sc, @Context UriInfo uriInfo)
	{
		ApiUser user = accountService.createUser(request);
		CreateUserResponse createUserResponse = new CreateUserResponse(user, createTokenForNewUser(user.getId(), request.getPassword().getPassword(),
				sc.getUserPrincipal().getName()));
		verificationTokenService.sendEmailRegistrationToken(createUserResponse.getApiUser().getId());
		URI location = uriInfo.getAbsolutePathBuilder().path(createUserResponse.getApiUser().getId()).build();
		return Response.created(location).entity(createUserResponse).build();
	}

	private OAuth2AccessToken createTokenForNewUser(String userId, String password, String clientId)
	{
		String hashedPassword = passwordEncoder.encode(password);
		UsernamePasswordAuthenticationToken userAuthentication = new UsernamePasswordAuthenticationToken(userId, hashedPassword,
				Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.toString())));
		ClientDetails authenticatedClient = clientDetailsService.loadClientByClientId(clientId);
		OAuth2Request oAuth2Request = createOAuth2Request(null, clientId,
				Collections.singleton(new SimpleGrantedAuthority(Role.ROLE_USER.toString())), true, authenticatedClient.getScope(), null, null, null,
				null);
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, userAuthentication);
		return tokenServices.createAccessToken(oAuth2Authentication);
	}

	private OAuth2Request createOAuth2Request(Map<String, String> requestParameters, String clientId,
			Collection<? extends GrantedAuthority> authorities, boolean approved, Collection<String> scope, Set<String> resourceIds,
			String redirectUri, Set<String> responseTypes, Map<String, Serializable> extensionProperties)
	{
		return new OAuth2Request(requestParameters, clientId, authorities, approved, scope == null ? null : new LinkedHashSet<String>(scope),
				resourceIds, redirectUri, responseTypes, extensionProperties);
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public Response sendEmailToken(@RequestBody LostPasswordRequest request)
	{
		verificationTokenService.sendLostPasswordToken(request);
		return Response.ok().build();
	}

	@RequestMapping(value = "/password/{token}", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public Response resetPassword(@PathVariable String base64EncodedToken, @RequestBody PasswordRequest request)
	{
		verificationTokenService.resetPassword(base64EncodedToken, request);
		return Response.ok().build();
	}

	@RequestMapping(value = "/tokens/{token}", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public Response verifyToken(@PathVariable String token)
	{
		verificationTokenService.verify(token);
		return Response.ok().build();
	}

	@RequestMapping(value = "/tokens", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	public Response sendEmailToken(@RequestBody EmailVerificationRequest request)
	{
		verificationTokenService.generateEmailVerificationToken(request.getEmailAddress());
		return Response.ok().build();
	}

	@RequestMapping(value = "/{accountId}/route-groups", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<RouteGroupResource> createRouteGroup(@PathVariable String accountId, @RequestBody RouteGroupResource res)
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails)
		{
			UserDetails details = (UserDetails) principal;
			Account loggedIn = accountService.findByAccountName(details.getUsername());
			if (loggedIn.getId() == accountId)
			{
				try
				{
					RouteGroup createdEntity = accountService.createRouteGroup(accountId, res.toRouteGroup());
					RouteGroupResource createdEntityRes = new RouteGroupResourceAsm().toResource(createdEntity);
					HttpHeaders headers = new HttpHeaders();
					headers.setLocation(URI.create(createdEntityRes.getLink("self").getHref()));
					return new ResponseEntity<RouteGroupResource>(createdEntityRes, headers, HttpStatus.CREATED);
				} catch (AccountDoesNotExistException exception)
				{
					throw new NotFoundException(exception);
				} catch (EntityAlreadyExistsException exception)
				{
					throw new ConflictException(exception);
				} catch (Exception exception)
				{
					throw new RuntimeException(exception.getCause());
				}
			} else
			{
				throw new ForbiddenException();
			}
		} else
		{
			throw new ForbiddenException();
		}
	}

	@RequestMapping(value = "/{accountId}/route-groups", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<RouteGroupListResource> findAllRouteGroups(@PathVariable String accountId)
	{
		try
		{
			RouteGroupList entityList = accountService.findRouteGroupsByAccount(accountId);
			RouteGroupListResource listResource = new RouteGroupListResourceAsm().toResource(entityList);
			return new ResponseEntity<RouteGroupListResource>(listResource, HttpStatus.OK);
		} catch (AccountDoesNotExistException exception)
		{
			throw new NotFoundException(exception);
		}
	}
}
