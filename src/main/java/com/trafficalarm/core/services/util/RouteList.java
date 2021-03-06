package com.trafficalarm.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.trafficalarm.core.model.entities.Route;

/**
 * Created by webyildirim on 6/28/14.
 */
public class RouteList {
    private List<Route> entities = new ArrayList<Route>();
    private String routeGroupId;

    public RouteList(String routeGroupId, List<Route> routes) {
        this.routeGroupId = routeGroupId;
        this.entities = routes;
    }

    public List<Route> getEntities() {
		return entities;
	}

	public void setEntities(List<Route> entities) {
		this.entities = entities;
	}

	public String getRouteGroupId() {
        return routeGroupId;
    }

    public void setRouteGroupId(String routeGroupId) {
        this.routeGroupId = routeGroupId;
    }
}
