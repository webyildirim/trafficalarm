package com.trafficalarm.core.model.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.structure.BaseEntity;

/**
 * Created by webyildirim on 6/28/14.
 */
@Entity
public class RouteGroup extends BaseEntity{

    private String title;

    @OneToOne
    private Account owner;

    @OneToMany(targetEntity = Route.class, mappedBy="routeGroup", cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
    private Collection<Route> routes;
    
    @OneToMany(targetEntity = RouteSchedule.class, mappedBy="routeGroup", cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
    private Collection<RouteSchedule> routeSchedules;

	public RouteGroup() {
		entityName="RouteGroup";
		routes=new ArrayList<Route>();
		routeSchedules=new ArrayList<RouteSchedule>();
	}
	
	public RouteGroup(String id) {
		entityName="RouteGroup";
		setId(id);
		routes=new ArrayList<Route>();
		routes=new ArrayList<Route>();
	}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Account getOwner() {
        return owner;
    }

    public void setOwner(Account owner) {
        this.owner = owner;
    }

	public Collection<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(Collection<Route> routes) {
		this.routes = routes;
	}

	public Collection<RouteSchedule> getRouteSchedules() {
		return routeSchedules;
	}

	public void setRouteSchedules(Collection<RouteSchedule> routeSchedules) {
		this.routeSchedules = routeSchedules;
	}

	@Override
	public String toString() {
		return getOwner().getName()+" "+getTitle();
	}
}
