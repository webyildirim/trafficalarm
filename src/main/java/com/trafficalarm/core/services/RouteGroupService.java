package com.trafficalarm.core.services;

import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.services.util.RouteList;
import com.trafficalarm.core.services.util.RouteSchedList;

/**
 * Created by webyildirim on 6/28/14.
 */
public interface RouteGroupService {

    public RouteGroup findRouteGroup(String id);
    public RouteGroup updateRouteGroup(String id, RouteGroup data) throws Exception;
    public RouteGroup deleteRouteGroup(String id) throws Exception; 
    public RouteList findAllRoutes(String routeGroupId); // findRoutes all associated routeGroup entries
    public RouteSchedList findAllRouteSchedules(String routeGroupId); // finds routeSchedules that all associated routeGroup entries
}
