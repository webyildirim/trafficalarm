package com.trafficalarm.core.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.services.util.AccountList;
import com.trafficalarm.core.services.util.RouteGroupList;
import com.trafficalarm.rest.api.user.ApiUser;
import com.trafficalarm.rest.api.user.CreateUserRequest;
import com.trafficalarm.rest.api.user.UpdateUserRequest;

/**
 * Created by webyildirim on 6/28/14.
 */
public interface AccountService {
    public Account findAccount(String id);
    public Account createAccount(Account data) throws Exception;
    public AccountList findAllAccounts();
    public Account findByAccountName(String name);
    
    public RouteGroup createRouteGroup(String accountId, RouteGroup data) throws Exception;
    public RouteGroupList findRouteGroupsByAccount(String accountId);
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    public ApiUser authenticate(String username, String password);
    public ApiUser getUser(String userId);
    public ApiUser createUser(CreateUserRequest createUserRequest);
    public ApiUser saveUser(String userId, UpdateUserRequest request);
}
