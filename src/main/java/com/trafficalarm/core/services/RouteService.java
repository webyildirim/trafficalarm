package com.trafficalarm.core.services;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.services.util.RouteDetailList;

/**
 * Created by webyildirim on 6/27/14.
 */
public interface RouteService {
	
    public Route findRoute(String id); 
    public Route updateRoute(String id, Route data) throws Exception;
    public Route deleteRoute(String id) throws Exception; 
    public Route createRoute(String routeGroupId, Route data) throws Exception; 
    public RouteDetailList findDetailsByRoute(String routeId);
}
