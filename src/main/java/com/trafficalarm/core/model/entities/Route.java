package com.trafficalarm.core.model.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.structure.BaseEntity;

/**
 * Created by webyildirim on 6/28/14.
 */
@Entity
public class Route extends BaseEntity{
    private String title;
    
    @OneToMany(targetEntity = RouteDetail.class, mappedBy="route", cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
    private Collection<RouteDetail> details;
    @ManyToOne
    private RouteGroup routeGroup;

	public Route() {
		entityName="Route";
		details=new ArrayList<RouteDetail>();
	}

	public Route(String id) {
		entityName="Route";
		setId(id);
		details=new ArrayList<RouteDetail>();
	}

    public RouteGroup getRouteGroup() {
		return routeGroup;
	}

	public void setRouteGroup(RouteGroup routeGroup) {
		this.routeGroup = routeGroup;
	}

	public Collection<RouteDetail> getDetails() {
		return details;
	}

	public void setDetails(Collection<RouteDetail> details) {
		this.details = details;
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

	@Override
	public String toString() {
		return getTitle();
	}
}
