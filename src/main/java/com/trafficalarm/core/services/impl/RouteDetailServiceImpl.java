package com.trafficalarm.core.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.model.entities.RouteDetail;
import com.trafficalarm.core.repositories.RouteDetailRepo;
import com.trafficalarm.core.repositories.RouteRepo;
import com.trafficalarm.core.services.RouteDetailService;
import com.trafficalarm.core.services.exceptions.EntityNotFoundException;

/**
 * Created by webyildirim on 7/10/14.
 */
@Service
@Transactional
public class RouteDetailServiceImpl implements RouteDetailService {

    @Autowired
    private RouteRepo routeRepo;
    @Autowired
    private RouteDetailRepo routeDetailRepo;

    @Override
    public RouteDetail findRouteDetail(String id) {
        return routeDetailRepo.findRouteDetail(id);
    }

    @Override
    public RouteDetail deleteRouteDetail(String id) throws Exception {
        RouteDetail routeDetail=routeDetailRepo.deleteRouteDetail(id);
        if(routeDetail==null)
        	throw new EntityNotFoundException(id, null, null);
        
        return routeDetail;
    }

    @Override
    public RouteDetail updateRouteDetail(String id, RouteDetail entity) throws Exception {
        return routeDetailRepo.updateRouteDetail(id, entity);
    }

	@Override
	public RouteDetail createRouteDetail(String routeId, RouteDetail data) throws Exception {
		Route route=routeRepo.findRoute(routeId);
		data.setRoute(route);
		routeDetailRepo.createRouteDetail(data);
		return data;
	}
}
