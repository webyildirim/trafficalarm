package com.trafficalarm.rest.mvc;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.services.AccountService;
import com.trafficalarm.core.services.exceptions.AccountDoesNotExistException;
import com.trafficalarm.core.services.exceptions.AccountExistsException;
import com.trafficalarm.core.services.exceptions.EntityAlreadyExistsException;
import com.trafficalarm.core.services.util.AccountList;
import com.trafficalarm.core.services.util.RouteGroupList;
import com.trafficalarm.rest.exceptions.ConflictException;
import com.trafficalarm.rest.exceptions.ForbiddenException;
import com.trafficalarm.rest.exceptions.NotFoundException;
import com.trafficalarm.rest.resources.AccountListResource;
import com.trafficalarm.rest.resources.AccountResource;
import com.trafficalarm.rest.resources.RouteGroupListResource;
import com.trafficalarm.rest.resources.RouteGroupResource;
import com.trafficalarm.rest.resources.asm.AccountListResourceAsm;
import com.trafficalarm.rest.resources.asm.AccountResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteGroupListResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteGroupResourceAsm;

/**
 * Created by webyildirim on 6/28/14.
 */
@Controller
@RequestMapping("/rest/accounts")
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ResponseEntity<AccountListResource> findAllAccounts(@RequestParam(value="name", required = false) String name, @RequestParam(value="password", required = false) String password) {
        AccountList list = null;
        if(name == null) {
            list = accountService.findAllAccounts();
        } else {
            Account account = accountService.findByAccountName(name);
            list = new AccountList(new ArrayList<Account>());
            if(account != null) {
                if(password != null) {
                    if(account.getPassword().equals(password)) {
                        list = new AccountList(Arrays.asList(account));
                    }
                } else {
                    list = new AccountList(Arrays.asList(account));
                }
            }
        }
        AccountListResource res = new AccountListResourceAsm().toResource(list);
        return new ResponseEntity<AccountListResource>(res, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("permitAll")
    public ResponseEntity<AccountResource> createAccount(
            @RequestBody AccountResource sentAccount
    ) {
        try {
            Account createdAccount = accountService.createAccount(sentAccount.toAccount());
            AccountResource res = new AccountResourceAsm().toResource(createdAccount);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(res.getLink("self").getHref()));
            return new ResponseEntity<AccountResource>(res, headers, HttpStatus.CREATED);
        } catch(AccountExistsException exception) {
            throw new ConflictException(exception);
        } catch(Exception exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception.getMessage());
        }
    }

    @RequestMapping( value="/{accountId}",
                method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ResponseEntity<AccountResource> getAccount(
            @PathVariable Long accountId
    ) {
        Account account = accountService.findAccount(accountId);
        if(account != null)
        {
            AccountResource res = new AccountResourceAsm().toResource(account);
            return new ResponseEntity<AccountResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value="/{accountId}/route-groups",
            method = RequestMethod.POST)
        @PreAuthorize("permitAll")
        public ResponseEntity<RouteGroupResource> createRouteGroup(
                @PathVariable Long accountId,
                @RequestBody RouteGroupResource res)
        {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(principal instanceof UserDetails) {
                UserDetails details = (UserDetails)principal;
                Account loggedIn = accountService.findByAccountName(details.getUsername());
                if(loggedIn.getId() == accountId) {
                    try {
                    	RouteGroup createdEntity = accountService.createRouteGroup(accountId, res.toRouteGroup());
                        RouteGroupResource createdEntityRes = new RouteGroupResourceAsm().toResource(createdEntity);
                        HttpHeaders headers = new HttpHeaders();
                        headers.setLocation(URI.create(createdEntityRes.getLink("self").getHref()));
                        return new ResponseEntity<RouteGroupResource>(createdEntityRes, headers, HttpStatus.CREATED);
                    } catch(AccountDoesNotExistException exception)
                    {
                        throw new NotFoundException(exception);
                    } catch(EntityAlreadyExistsException exception)
                    {
                        throw new ConflictException(exception);
                    }catch(Exception exception)
                    {
                        throw new RuntimeException(exception.getCause());
                    }
                } else {
                    throw new ForbiddenException();
                }
            } else {
                throw new ForbiddenException();
            }
        }

    @RequestMapping(value="/{accountId}/route-groups",
            method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ResponseEntity<RouteGroupListResource> findAllRouteGroups(
            @PathVariable Long accountId) {
        try {
            RouteGroupList entityList = accountService.findRouteGroupsByAccount(accountId);
            RouteGroupListResource listResource = new RouteGroupListResourceAsm().toResource(entityList);
            return new ResponseEntity<RouteGroupListResource>(listResource, HttpStatus.OK);
        } catch(AccountDoesNotExistException exception)
        {
            throw new NotFoundException(exception);
        }
    }

}
