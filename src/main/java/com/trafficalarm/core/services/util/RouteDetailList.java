package com.trafficalarm.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.trafficalarm.core.model.entities.RouteDetail;

/**
 * Created by webyildirim on 6/28/14.
 */
public class RouteDetailList {
    private List<RouteDetail> details = new ArrayList<RouteDetail>();
    private String routeId;

    public RouteDetailList(String routeId, List<RouteDetail> details) {
        this.routeId = routeId;
        this.details = details;
    }

    public List<RouteDetail> getDetails() {
		return details;
	}

	public void setDetails(List<RouteDetail> details) {
		this.details = details;
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
}
