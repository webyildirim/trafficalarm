package com.trafficalarm.core.repositories;

import com.trafficalarm.core.model.entities.Route;

import java.util.List;

/**
 * Created by webyildirim on 7/10/14.
 */
public interface RouteRepo {
	/**
	 * Returns the Route or null if it can't be found
	 * @param id
	 * @return
	 */
    public Route findRoute(Long id);
    
    /**
     * Deletes the found Route or returns null if it can't be found
     * @param id
     * @return
     * @throws Exception 
     */
    public Route deleteRoute(Long id) throws Exception; 

    /**
     * @param id the id of the Route to updateBlogEntry
     * @param data the Route containing the data to be used for the updateBlogEntry
     * @return the updated BlogEntry or null if the BlogEntry with the id cannot be found
     * @throws Exception 
     */
    public Route updateRoute(Long id, Route data) throws Exception;

    public Route createRoute(Route data) throws Exception;

    public List<Route> findByRouteGroupId(Long routeGroupId);
}
