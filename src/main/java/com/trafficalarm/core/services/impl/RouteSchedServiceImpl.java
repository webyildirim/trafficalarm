package com.trafficalarm.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.model.entities.RouteSchedule;
import com.trafficalarm.core.repositories.RouteGroupRepo;
import com.trafficalarm.core.repositories.RouteScheduleRepo;
import com.trafficalarm.core.services.RouteSchedService;
import com.trafficalarm.core.services.exceptions.EntityNotFoundException;

/**
 * Created by webyildirim on 7/10/14.
 */
@Service
@Transactional
public class RouteSchedServiceImpl implements RouteSchedService{

    @Autowired
    private RouteScheduleRepo routeScheduleRepo;
    @Autowired
    private RouteGroupRepo routeGroupRepo;

    @Override
    public RouteSchedule findRouteSched(String id) {
        return routeScheduleRepo.findRouteSchedule(id);
    }

    @Override
    public RouteSchedule deleteRouteSched(String id) throws Exception {
        RouteSchedule routeSched=routeScheduleRepo.deleteRouteSchedule(id);
        if(routeSched==null)
        	throw new EntityNotFoundException(id, null, null);
        
        return routeSched;
    }

	@Override
	public RouteSchedule createRouteSched(String routeGroupId, RouteSchedule data) throws Exception {
		RouteGroup routeGroup=routeGroupRepo.findRouteGroup(routeGroupId);
		data.setRouteGroup(routeGroup);
		routeScheduleRepo.createRouteSchedule(data);
		return data;
	}
}
