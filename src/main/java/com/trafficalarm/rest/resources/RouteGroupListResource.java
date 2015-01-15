package com.trafficalarm.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteGroupListResource extends ResourceSupport {
    private List<RouteGroupResource> routeGroups = new ArrayList<RouteGroupResource>();

    public List<RouteGroupResource> getRouteGroups() {
        return routeGroups;
    }

    public void setRouteGroups(List<RouteGroupResource> routeGroups) {
        this.routeGroups = routeGroups;
    }
}
