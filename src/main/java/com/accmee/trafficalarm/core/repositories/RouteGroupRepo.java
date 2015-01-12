package com.accmee.trafficalarm.core.repositories;

import java.util.List;

import com.accmee.structure.BaseFilter;
import com.accmee.trafficalarm.core.models.entities.RouteGroup;

/**
 * Created by webyildirim on 7/10/14.
 */
public interface RouteGroupRepo {
    public RouteGroup createRouteGroup(RouteGroup data) throws Exception;
    public RouteGroup findRouteGroup(Long id);
    public RouteGroup deleteRouteGroup(Long id) throws Exception; 
    public RouteGroup findRouteGroupByFilter(BaseFilter filter, String queryMethod);
    public List<RouteGroup> findRouteGroupsByAccount(Long accountId);
}
