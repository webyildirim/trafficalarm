package com.trafficalarm.core.model.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.structure.BaseEntity;

/**
 * Created by webyildirim on 6/28/14.
 */
@Entity
public class RouteDetail extends BaseEntity {
    private String title;
    private String coordinate;
    
    @ManyToOne
    private Route route;

	public RouteDetail() {
		entityName="RouteDetail";		
	}

    public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return coordinate;
	}
}
