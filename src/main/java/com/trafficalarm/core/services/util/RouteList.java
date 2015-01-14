package com.trafficalarm.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.trafficalarm.core.model.entities.Route;

/**
 * Created by webyildirim on 6/28/14.
 */
public class RouteList {
    private List<Route> routes = new ArrayList<Route>();
    private Long routeGroupId;

    public RouteList(Long routeGroupId, List<Route> routes) {
        this.routeGroupId = routeGroupId;
        this.routes = routes;
    }

    public List<Route> getEntries() {
        return routes;
    }

    public void setEntries(List<Route> routes) {
        this.routes = routes;
    }

    public Long getRouteGroupId() {
        return routeGroupId;
    }

    public void setRouteGroupId(Long routeGroupId) {
        this.routeGroupId = routeGroupId;
    }
}
