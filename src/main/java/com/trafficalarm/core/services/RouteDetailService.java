package com.trafficalarm.core.services;

import com.trafficalarm.core.model.entities.RouteDetail;

/**
 * Created by webyildirim on 6/27/14.
 */
public interface RouteDetailService {
	
    public RouteDetail findRouteDetail(String id); 
    public RouteDetail updateRouteDetail(String id, RouteDetail data) throws Exception;
    public RouteDetail deleteRouteDetail(String id) throws Exception; 
    public RouteDetail createRouteDetail(String routeId, RouteDetail data) throws Exception; 
}
