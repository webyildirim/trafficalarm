package com.trafficalarm.core.repositories;

import java.util.List;

import com.trafficalarm.core.model.entities.Route;

/**
 * Created by webyildirim on 7/10/14.
 */
public interface RouteRepo {
	/**
	 * Returns the Route or null if it can't be found
	 * @param id
	 * @return
	 */
    public Route findRoute(String id);
    
    /**
     * Deletes the found Route or returns null if it can't be found
     * @param id
     * @return
     * @throws Exception 
     */
    public Route deleteRoute(String id) throws Exception; 

    /**
     * @param id the id of the Route to updateRoute
     * @param data the Route containing the data to be used for the updateRoute
     * @return the updated updateRoute or null if the Route with the id cannot be found
     * @throws Exception 
     */
    public Route updateRoute(String id, Route data) throws Exception;

    public Route createRoute(Route data) throws Exception;

    public List<Route> findByRouteGroup(String routeGroupId);
}
