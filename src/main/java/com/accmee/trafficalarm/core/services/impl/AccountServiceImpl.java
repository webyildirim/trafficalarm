package com.accmee.trafficalarm.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accmee.trafficalarm.core.models.entities.Account;
import com.accmee.trafficalarm.core.models.entities.Blog;
import com.accmee.trafficalarm.core.models.entities.RouteGroup;
import com.accmee.trafficalarm.core.models.filter.RouteGroupFilter;
import com.accmee.trafficalarm.core.repositories.AccountRepo;
import com.accmee.trafficalarm.core.repositories.BlogRepo;
import com.accmee.trafficalarm.core.repositories.RouteGroupRepo;
import com.accmee.trafficalarm.core.services.AccountService;
import com.accmee.trafficalarm.core.services.exceptions.AccountDoesNotExistException;
import com.accmee.trafficalarm.core.services.exceptions.AccountExistsException;
import com.accmee.trafficalarm.core.services.exceptions.EntityAlreadyExistsException;
import com.accmee.trafficalarm.core.services.util.AccountList;
import com.accmee.trafficalarm.core.services.util.BlogList;
import com.accmee.trafficalarm.core.services.util.RouteGroupList;

/**
 * Created by webyildirim on 7/10/14.
 */
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private BlogRepo blogRepo;

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
    public Blog createBlog(Long accountId, Blog data) {
        Blog blogSameTitle = blogRepo.findBlogByTitle(data.getTitle());

        if(blogSameTitle != null)
        {
            throw new EntityAlreadyExistsException("Blog zaten mevcut");
        }

        Account account = accountRepo.findAccount(accountId);
        if(account == null)
        {
            throw new AccountDoesNotExistException();
        }

        Blog createdBlog = blogRepo.createBlog(data);

        createdBlog.setOwner(account);

        return createdBlog;
    }

    @Override
    public BlogList findBlogsByAccount(Long accountId) {
        Account account = accountRepo.findAccount(accountId);
        if(account == null)
        {
            throw new AccountDoesNotExistException();
        }
        return new BlogList(blogRepo.findBlogsByAccount(accountId));
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
