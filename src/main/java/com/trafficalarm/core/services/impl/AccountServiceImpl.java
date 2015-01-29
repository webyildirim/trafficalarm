package com.trafficalarm.core.services.impl;

import static org.springframework.util.Assert.notNull;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.core.model.entities.Role;
import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.model.filter.RouteGroupFilter;
import com.trafficalarm.core.repositories.AccountRepo;
import com.trafficalarm.core.repositories.RouteGroupRepo;
import com.trafficalarm.core.services.AccountService;
import com.trafficalarm.core.services.base.BaseService;
import com.trafficalarm.core.services.exceptions.AccountDoesNotExistException;
import com.trafficalarm.core.services.exceptions.AccountExistsException;
import com.trafficalarm.core.services.exceptions.EntityAlreadyExistsException;
import com.trafficalarm.core.services.util.AccountList;
import com.trafficalarm.core.services.util.RouteGroupList;
import com.trafficalarm.rest.api.user.ApiUser;
import com.trafficalarm.rest.api.user.CreateUserRequest;
import com.trafficalarm.rest.api.user.UpdateUserRequest;
import com.trafficalarm.rest.exceptions.user.AuthenticationException;
import com.trafficalarm.rest.exceptions.user.DuplicateUserException;
import com.trafficalarm.rest.exceptions.user.UserNotFoundException;

/**
 * Created by webyildirim on 7/10/14.
 */
@Service
@Transactional
public class AccountServiceImpl extends BaseService implements AccountService,UserDetailsService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private RouteGroupRepo routeGroupRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(final AccountRepo accountRepo, Validator validator,
                           PasswordEncoder passwordEncoder) {
        super(validator);
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Account findAccount(String id) {
        return accountRepo.findAccount(id);
    }

    @Override
    public Account createAccount(Account data) throws Exception {
        Account account = accountRepo.findAccountByName(data.getName());
        if(account != null)
        {
            throw new AccountExistsException();
        }
        return accountRepo.createAccount(data);
    }

    @Override
    public AccountList findAllAccounts() {
        return new AccountList(accountRepo.findAllAccounts());
    }

    @Override
    public Account findByAccountName(String name) {
        return accountRepo.findAccountByName(name);
    }

    @Override
    public RouteGroup createRouteGroup(String accountId, RouteGroup routeGroup) throws Exception {

        Account account = accountRepo.findAccount(accountId);
        if(account == null)
        {
            throw new AccountDoesNotExistException();
        }
        
    	routeGroup.setOwner(account);
        RouteGroup foundRouteGroup = routeGroupRepo.findRouteGroupByFilter(new RouteGroupFilter(routeGroup), null);
        if(foundRouteGroup != null)
        {
            throw new EntityAlreadyExistsException("Rota ismi zaten mevcut");
        }

        routeGroup = routeGroupRepo.createRouteGroup(routeGroup);

        return routeGroup;
    }

    @Override
    public RouteGroupList findRouteGroupsByAccount(String accountId) {
        Account account = accountRepo.findAccount(accountId);
        if(account == null)
        {
            throw new AccountDoesNotExistException();
        }
        return new RouteGroupList(routeGroupRepo.findRouteGroupsByAccount(accountId));
    }
    //

    @Override
    public ApiUser authenticate(String username, String password) {
        Assert.notNull(username);
        Assert.notNull(password);
        Account user = locateUser(username);
        if(!passwordEncoder.encode(password).equals(user.getPassword())) {
            throw new AuthenticationException();
        }
        return new ApiUser(user);
    }
    
    @Override
    public ApiUser createUser(final CreateUserRequest createUserRequest) {

        //LOG.info("Validating user request.");
        validate(createUserRequest);
        final String emailAddress = createUserRequest.getUser().getEmailAddress().toLowerCase();
        if (accountRepo.findAccountByName(emailAddress) == null) {
//            LOG.info("User does not already exist in the data store - creating a new user [{}].", emailAddress);
            Account newUser = insertNewUser(createUserRequest);
//            LOG.debug("Created new user [{}].", newUser.getEmailAddress());
            return new ApiUser(newUser);
        } else {
//            LOG.info("Duplicate user located, exception raised with appropriate HTTP response code.");
            throw new DuplicateUserException();
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) locateUser(username);
    }

    @Override
    public ApiUser saveUser(String userId, UpdateUserRequest request) {
        validate(request);
        Account user = accountRepo.findAccount(userId);
        if(user == null) {
            throw new UserNotFoundException();
        }
        if(request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }
        if(request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }
        if(request.getEmailAddress() != null) {
            if(!request.getEmailAddress().equals(user.getName())) {
                user.setName(request.getEmailAddress());
                user.setPassive(true);
            }
        }
        try
		{
			user = accountRepo.createAccount(user);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
        return new ApiUser(user);
    }

    @Override
    public ApiUser getUser(String userId) {
        Assert.notNull(userId);
        Account user = findAccount(userId);
        if(user == null) {
            throw new UserNotFoundException();
        }
        return new ApiUser(user);
    }

    /**
     * Locate the user and throw an exception if not found.
     *
     * @param username
     * @return a User object is guaranteed.
     * @throws AuthenticationException if user not located.
     */
    private Account locateUser(final String username) {
        notNull(username, "Mandatory argument 'username' missing.");
        Account user = accountRepo.findAccountByName(username.toLowerCase());
        if (user == null) {
//            LOG.debug("Credentials [{}] failed to locate a user.", username.toLowerCase());
            throw new UsernameNotFoundException("failed to locate a user");
        }
        return user;
    }

    private Account insertNewUser(final CreateUserRequest createUserRequest) {
        String hashedPassword = passwordEncoder.encode(createUserRequest.getPassword().getPassword());
        Account newUser = new Account(createUserRequest.getUser(), hashedPassword, Role.ROLE_USER);
        try {
			newUser= accountRepo.createAccount(newUser);
		} catch (Exception e) {
			e.printStackTrace();
//			LOG.error("Exception while inserting new user", newUser.getName());
		}
        return newUser;
    }
}
