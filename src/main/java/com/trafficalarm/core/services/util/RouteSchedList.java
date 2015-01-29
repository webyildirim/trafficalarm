package com.trafficalarm.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.trafficalarm.core.model.entities.RouteSchedule;

/**
 * Created by webyildirim on 7/1/14.
 */
public class RouteSchedList {

    private List<RouteSchedule> routeSchedules = new ArrayList<RouteSchedule>();
    private String routeGroupId;

    public RouteSchedList(String routeGroupId, List<RouteSchedule> resultList) {
        this.routeGroupId = routeGroupId;
        this.routeSchedules = resultList;
    }

	public List<RouteSchedule> getRouteSchedules() {
		return routeSchedules;
	}

	public void setRouteSchedules(List<RouteSchedule> routeSchedules) {
		this.routeSchedules = routeSchedules;
	}

	public String getRouteGroupId() {
		return routeGroupId;
	}

	public void setRouteGroupId(String routeGroupId) {
		this.routeGroupId = routeGroupId;
	}
}
