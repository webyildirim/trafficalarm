package com.accmee.trafficalarm.core.services;

import com.accmee.trafficalarm.core.models.entities.RouteGroup;
import com.accmee.trafficalarm.core.services.util.RouteList;

/**
 * Created by webyildirim on 6/28/14.
 */
public interface RouteGroupService {

    public RouteList findAllRoutes(Long account); // findRoutes all associated routeGroup entries
    public RouteGroup findRouteGroup(Long id);
    public RouteGroup deleteRouteGroup(Long id) throws Exception; 
}
