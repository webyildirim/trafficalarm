package com.trafficalarm.core.services;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.services.util.RouteDetailList;

/**
 * Created by webyildirim on 6/27/14.
 */
public interface RouteService {
	
    public Route findRoute(Long id); 
    public Route updateRoute(Long id, Route data) throws Exception;
    public Route deleteRoute(Long id) throws Exception; 
    public Route createRoute(Long routeGroupId, Route data) throws Exception; 
    public RouteDetailList findDetailsByRoute(Long routeId);
}
