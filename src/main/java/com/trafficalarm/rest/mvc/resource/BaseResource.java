package com.trafficalarm.rest.mvc.resource;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.core.repositories.AccountRepo;
import com.trafficalarm.rest.oauth2.AuthorizationException;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 07/05/2013
 */
public class BaseResource {

    @Autowired
    private AccountRepo userRepository;

    //TODO: Cache to reduce calls to accountRepo
    protected Account ensureUserIsAuthorized(SecurityContext securityContext, String userId) {
    	Account user = loadUserFromSecurityContext(securityContext);
        if (user != null && (user.getId().equals(userId) || user.getName().equals(userId.toLowerCase()))) {
            return user;
        }
        throw new AuthorizationException("User not permitted to access this resource");

    }

    protected Account loadUserFromSecurityContext(SecurityContext securityContext) {
        OAuth2Authentication requestingUser = (OAuth2Authentication) securityContext.getUserPrincipal();
        Object principal = requestingUser.getUserAuthentication().getPrincipal();
        Account user = null;
        if(principal instanceof Account) {
            user = (Account)principal;
        } else {
            user = userRepository.findAccount((String)principal);
        }
        return user;
    }
}
