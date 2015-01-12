package com.accmee.trafficalarm.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteListResource extends ResourceSupport {
    private List<RouteResource> routes = new ArrayList<RouteResource>();

    public List<RouteResource> getRoutes() {
        return routes;
    }

    public void setBlogs(List<RouteResource> routes) {
        this.routes = routes;
    }
}
