package com.trafficalarm.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.model.entities.RouteDetail;
import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.model.entities.RouteSchedule;
import com.trafficalarm.core.repositories.RouteGroupRepo;
import com.trafficalarm.core.repositories.RouteRepo;
import com.trafficalarm.core.services.RouteGroupService;
import com.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.trafficalarm.core.services.util.RouteSchedList;
import com.trafficalarm.core.services.util.RouteList;

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
    public RouteGroup findRouteGroup(Long id) {
        return routeGroupRepo.findRouteGroup(id);
    }

    @Override
    public RouteGroup updateRouteGroup(Long id, RouteGroup entity) throws Exception {
        return routeGroupRepo.updateRouteGroup(id, entity);
    }

	@Override
	public RouteGroup deleteRouteGroup(Long id) throws Exception {
        RouteGroup routeGroup=routeGroupRepo.deleteRouteGroup(id);
        if(routeGroup==null)
        	throw new EntityNotFoundException(id, null, null);
        
        return routeGroup;
	}
    
    @Override
    public RouteList findAllRoutes(Long routeGroupId) {
    	RouteGroup routeGroup = routeGroupRepo.findRouteGroup(routeGroupId);
        if(routeGroup == null)
        {
            throw new EntityNotFoundException();
        }
        return new RouteList(routeGroupId, (List<Route>) routeGroup.getRoutes());
    }
    
    @Override
    public RouteSchedList findAllRouteSchedules(Long routeGroupId) {
    	RouteGroup routeGroup = routeGroupRepo.findRouteGroup(routeGroupId);
        if(routeGroup == null)
        {
            throw new EntityNotFoundException();
        }
        return new RouteSchedList(routeGroupId, (List<RouteSchedule>) routeGroup.getRouteSchedules());
    }
}
