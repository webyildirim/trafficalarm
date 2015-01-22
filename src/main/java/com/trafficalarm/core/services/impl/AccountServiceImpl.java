package com.trafficalarm.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.model.filter.RouteGroupFilter;
import com.trafficalarm.core.repositories.AccountRepo;
import com.trafficalarm.core.repositories.RouteGroupRepo;
import com.trafficalarm.core.services.AccountService;
import com.trafficalarm.core.services.exceptions.AccountDoesNotExistException;
import com.trafficalarm.core.services.exceptions.AccountExistsException;
import com.trafficalarm.core.services.exceptions.EntityAlreadyExistsException;
import com.trafficalarm.core.services.util.AccountList;
import com.trafficalarm.core.services.util.RouteGroupList;

/**
 * Created by webyildirim on 7/10/14.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private RouteGroupRepo routeGroupRepo;

    @Override
    public Account findAccount(Long id) {
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
    public RouteGroup createRouteGroup(Long accountId, RouteGroup routeGroup) throws Exception {

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
    public RouteGroupList findRouteGroupsByAccount(Long accountId) {
        Account account = accountRepo.findAccount(accountId);
        if(account == null)
        {
            throw new AccountDoesNotExistException();
        }
        return new RouteGroupList(routeGroupRepo.findRouteGroupsByAccount(accountId));
    }
}
