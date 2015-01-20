package com.trafficalarm.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteSchedListResource extends ResourceSupport {
	
    private List<RouteSchedResource> schedules = new ArrayList<RouteSchedResource>();

	public List<RouteSchedResource> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<RouteSchedResource> schedules) {
		this.schedules = schedules;
	}

   
}
