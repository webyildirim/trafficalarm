package com.accmee.trafficalarm.core.services;

import com.accmee.trafficalarm.core.models.entities.Account;
import com.accmee.trafficalarm.core.models.entities.Blog;
import com.accmee.trafficalarm.core.models.entities.RouteGroup;
import com.accmee.trafficalarm.core.services.util.AccountList;
import com.accmee.trafficalarm.core.services.util.BlogList;
import com.accmee.trafficalarm.core.services.util.RouteGroupList;

/**
 * Created by webyildirim on 6/28/14.
 */
public interface AccountService {
    public Account findAccount(Long id);
    public Account createAccount(Account data) throws Exception;
    public Blog createBlog(Long accountId, Blog data);
    public BlogList findBlogsByAccount(Long accountId);
    public AccountList findAllAccounts();
    public Account findByAccountName(String name);
    
    public RouteGroup createRouteGroup(Long accountId, RouteGroup data) throws Exception;
    public RouteGroupList findRouteGroupsByAccount(Long accountId);
}
