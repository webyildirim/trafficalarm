package com.trafficalarm.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.model.entities.RouteSchedule;
import com.trafficalarm.core.repositories.RouteGroupRepo;
import com.trafficalarm.core.repositories.RouteRepo;
import com.trafficalarm.core.services.RouteGroupService;
import com.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.trafficalarm.core.services.util.RouteList;
import com.trafficalarm.core.services.util.RouteSchedList;

/**
 * Created by webyildirim on 7/10/14.
 */
@Service
@Transactional
public class RouteGroupServiceImpl implements RouteGroupService {

    @Autowired
    private RouteGroupRepo routeGroupRepo;

    @Autowired
    private RouteRepo routeRepo;

    @Override
    public RouteGroup findRouteGroup(String id) {
        return routeGroupRepo.findRouteGroup(id);
    }

    @Override
    public RouteGroup updateRouteGroup(String id, RouteGroup entity) throws Exception {
        return routeGroupRepo.updateRouteGroup(id, entity);
    }

	@Override
	public RouteGroup deleteRouteGroup(String id) throws Exception {
        RouteGroup routeGroup=routeGroupRepo.deleteRouteGroup(id);
        if(routeGroup==null)
        	throw new EntityNotFoundException(id, null, null);
        
        return routeGroup;
	}
    
    @Override
    public RouteList findAllRoutes(String routeGroupId) {
    	RouteGroup routeGroup = routeGroupRepo.findRouteGroup(routeGroupId);
        if(routeGroup == null)
        {
            throw new EntityNotFoundException();
        }
        return new RouteList(routeGroupId, (List<Route>) routeGroup.getRoutes());
    }
    
    @Override
    public RouteSchedList findAllRouteSchedules(String routeGroupId) {
    	RouteGroup routeGroup = routeGroupRepo.findRouteGroup(routeGroupId);
        if(routeGroup == null)
        {
            throw new EntityNotFoundException();
        }
        return new RouteSchedList(routeGroupId, (List<RouteSchedule>) routeGroup.getRouteSchedules());
    }
}
