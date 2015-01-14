package com.trafficalarm.core.repositories;

import java.util.List;

import com.trafficalarm.core.model.entities.RouteDetail;

/**
 * Created by webyildirim on 7/10/14.
 */
public interface RouteDetailRepo {
	
    public RouteDetail findRouteDetail(Long id);
    
    public RouteDetail deleteRouteDetail(Long id) throws Exception; 

    public RouteDetail updateRouteDetail(Long id, RouteDetail data) throws Exception;

    public RouteDetail createRouteDetail(RouteDetail data) throws Exception;

    public List<RouteDetail> findByRouteId(Long routeId);
}
