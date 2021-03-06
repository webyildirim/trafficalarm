package com.trafficalarm.core.repositories;

import java.util.List;

import com.structure.BaseFilter;
import com.trafficalarm.core.model.entities.RouteGroup;

/**
 * Created by webyildirim on 7/10/14.
 */
public interface RouteGroupRepo {
    public RouteGroup createRouteGroup(RouteGroup data) throws Exception;
    public RouteGroup findRouteGroup(String id);
    public RouteGroup deleteRouteGroup(String id) throws Exception; 
    public RouteGroup findRouteGroupByFilter(BaseFilter filter, String queryMethod);
    public List<RouteGroup> findRouteGroupsByAccount(String accountId);

    /**
     * @param id the id of the Route to updateRoute
     * @param data the Route containing the data to be used for the updateRoute
     * @return the updated updateRoute or null if the Route with the id cannot be found
     * @throws Exception 
     */
    public RouteGroup updateRouteGroup(String id, RouteGroup data) throws Exception;
}
