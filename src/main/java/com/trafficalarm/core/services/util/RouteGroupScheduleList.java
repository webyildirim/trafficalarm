package com.trafficalarm.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.trafficalarm.core.model.entities.RouteGroupSchedule;

/**
 * Created by webyildirim on 7/1/14.
 */
public class RouteGroupScheduleList {

    private List<RouteGroupSchedule> routeGroups = new ArrayList<RouteGroupSchedule>();
    private Long routeGroupId;

    public RouteGroupScheduleList(Long routeGroupId, List<RouteGroupSchedule> resultList) {
        this.routeGroupId = routeGroupId;
        this.routeGroups = resultList;
    }

	public List<RouteGroupSchedule> getRouteGroups() {
		return routeGroups;
	}

	public void setRouteGroups(List<RouteGroupSchedule> routeGroups) {
		this.routeGroups = routeGroups;
	}

	public Long getRouteGroupId() {
		return routeGroupId;
	}

	public void setRouteGroupId(Long routeGroupId) {
		this.routeGroupId = routeGroupId;
	}
}
