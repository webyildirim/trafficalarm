package com.trafficalarm.core.services;

import com.trafficalarm.core.model.entities.RouteSchedule;

/**
 * Created by webyildirim on 6/27/14.
 */
public interface RouteSchedService {
	
    public RouteSchedule findRouteSched(String id); 
    public RouteSchedule deleteRouteSched(String id) throws Exception; 
    public RouteSchedule createRouteSched(String routeGroupId, RouteSchedule data) throws Exception; 
}
