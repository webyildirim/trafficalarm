package com.trafficalarm.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

import com.trafficalarm.core.model.entities.RouteGroupSchedule;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteGroupSchedListResource extends ResourceSupport {
	
    private List<RouteGroupSchedule> schedules = new ArrayList<RouteGroupSchedule>();

	public List<RouteGroupSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<RouteGroupSchedule> schedules) {
		this.schedules = schedules;
	}

   
}
