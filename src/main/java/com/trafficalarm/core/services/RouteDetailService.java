package com.trafficalarm.core.services;

import com.trafficalarm.core.model.entities.RouteDetail;

/**
 * Created by webyildirim on 6/27/14.
 */
public interface RouteDetailService {
	
    public RouteDetail findRouteDetail(Long id); 
    public RouteDetail updateRouteDetail(Long id, RouteDetail data) throws Exception;
    public RouteDetail deleteRouteDetail(Long id) throws Exception; 
    public RouteDetail createRouteDetail(Long routeId, RouteDetail data) throws Exception; 
}
