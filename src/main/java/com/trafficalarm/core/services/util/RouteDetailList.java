package com.trafficalarm.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.trafficalarm.core.model.entities.RouteDetail;

/**
 * Created by webyildirim on 6/28/14.
 */
public class RouteDetailList {
    private List<RouteDetail> details = new ArrayList<RouteDetail>();
    private Long routeId;

    public RouteDetailList(Long routeId, List<RouteDetail> details) {
        this.routeId = routeId;
        this.details = details;
    }

    public List<RouteDetail> getDetails() {
		return details;
	}

	public void setDetails(List<RouteDetail> details) {
		this.details = details;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
}
