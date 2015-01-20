package com.trafficalarm.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.trafficalarm.core.model.entities.RouteSchedule;

/**
 * Created by webyildirim on 7/1/14.
 */
public class RouteSchedList {

    private List<RouteSchedule> routeSchedules = new ArrayList<RouteSchedule>();
    private Long routeGroupId;

    public RouteSchedList(Long routeGroupId, List<RouteSchedule> resultList) {
        this.routeGroupId = routeGroupId;
        this.routeSchedules = resultList;
    }

	public List<RouteSchedule> getRouteSchedules() {
		return routeSchedules;
	}

	public void setRouteSchedules(List<RouteSchedule> routeSchedules) {
		this.routeSchedules = routeSchedules;
	}

	public Long getRouteGroupId() {
		return routeGroupId;
	}

	public void setRouteGroupId(Long routeGroupId) {
		this.routeGroupId = routeGroupId;
	}
}
