package com.trafficalarm.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.model.entities.RouteDetail;

/**
 * Created by webyildirim
 */
public class RouteDetailResource extends ResourceSupport {
	
	private String coordinate;
    private String title;
    private String routeId;

    private String rid;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public RouteDetail toRoute() {
    	RouteDetail entity = new RouteDetail();
    	entity.setTitle(title);
        entity.setCoordinate(coordinate);;
        entity.setRoute(new Route(routeId));
        return entity;
    }
}
