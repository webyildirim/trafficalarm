package com.trafficalarm.core.services;

import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.services.util.RouteList;

/**
 * Created by webyildirim on 6/28/14.
 */
public interface RouteGroupService {

    public RouteGroup findRouteGroup(Long id);
    public RouteGroup deleteRouteGroup(Long id) throws Exception; 
    public RouteList findAllRoutes(Long routeGroupId); // findRoutes all associated routeGroup entries
}
