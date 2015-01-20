package com.trafficalarm.core.services;

import com.trafficalarm.core.model.entities.RouteSchedule;

/**
 * Created by webyildirim on 6/27/14.
 */
public interface RouteSchedService {
	
    public RouteSchedule findRouteSched(Long id); 
    public RouteSchedule deleteRouteSched(Long id) throws Exception; 
    public RouteSchedule createRouteSched(Long routeGroupId, RouteSchedule data) throws Exception; 
}
