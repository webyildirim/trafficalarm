package com.accmee.trafficalarm.core.services;

import com.accmee.trafficalarm.core.models.entities.Route;

/**
 * Created by webyildirim on 6/27/14.
 */
public interface RouteService {
	
    public Route findRoute(Long id); 
    public Route updateRoute(Long id, Route data) throws Exception;
    public Route deleteRoute(Long id) throws Exception; 

}
