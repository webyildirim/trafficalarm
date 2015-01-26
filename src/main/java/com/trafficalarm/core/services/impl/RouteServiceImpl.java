package com.trafficalarm.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.model.entities.RouteDetail;
import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.repositories.RouteGroupRepo;
import com.trafficalarm.core.repositories.RouteRepo;
import com.trafficalarm.core.services.RouteService;
import com.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.trafficalarm.core.services.util.RouteDetailList;

/**
 * Created by webyildirim on 7/10/14.
 */
@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepo routeRepo;
    @Autowired
    private RouteGroupRepo routeGroupRepo;

    @Override
    public Route findRoute(Long id) {
    	Route route = routeRepo.findRoute(id);
        if(route == null)
        {
            throw new EntityNotFoundException();
        }
        return route;
    }

    @Override
    public Route deleteRoute(Long id) throws Exception {
        Route route=routeRepo.deleteRoute(id);
        if(route==null)
        	throw new EntityNotFoundException(id, null, null);
        
        return route;
    }

    @Override
    public Route updateRoute(Long id, Route entity) throws Exception {
        return routeRepo.updateRoute(id, entity);
    }

	@Override
	public Route createRoute(Long routeGroupId, Route data) throws Exception {
		RouteGroup routeGroup=routeGroupRepo.findRouteGroup(routeGroupId);
		data.setRouteGroup(routeGroup);
		routeRepo.createRoute(data);
		return data;
	}

	@Override
	public RouteDetailList findDetailsByRoute(Long routeId) {
    	Route route = routeRepo.findRoute(routeId);
        if(route == null)
        {
            throw new EntityNotFoundException();
        }
        return new RouteDetailList(routeId, (List<RouteDetail>) route.getDetails());
	}
}
