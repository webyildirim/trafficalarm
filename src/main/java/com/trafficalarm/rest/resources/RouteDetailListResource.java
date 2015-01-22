package com.trafficalarm.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteDetailListResource extends ResourceSupport {
    private List<RouteDetailResource> routeDetails = new ArrayList<RouteDetailResource>();

	public List<RouteDetailResource> getRouteDetails() {
		return routeDetails;
	}

	public void setRouteDetails(List<RouteDetailResource> routeDetails) {
		this.routeDetails = routeDetails;
	}

}
