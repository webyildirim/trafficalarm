package com.trafficalarm.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.trafficalarm.core.model.entities.RouteGroup;

/**
 * Created by webyildirim on 7/1/14.
 */
public class RouteGroupList {

    private List<RouteGroup> routeGroups = new ArrayList<RouteGroup>();

    public RouteGroupList(List<RouteGroup> resultList) {
        this.routeGroups = resultList;
    }

	public List<RouteGroup> getRouteGroups() {
		return routeGroups;
	}

	public void setRouteGroups(List<RouteGroup> routeGroups) {
		this.routeGroups = routeGroups;
	}

}
