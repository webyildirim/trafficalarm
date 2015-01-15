package com.trafficalarm.core.model.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.structure.BaseEntity;

/**
 * Created by webyildirim on 6/28/14.
 */
@Entity
public class Route extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    
    @OneToMany(targetEntity = RouteDetail.class, mappedBy="route", cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
    private Collection<RouteDetail> details;
    @ManyToOne
    private RouteGroup routeGroup;

	public Route() {
		entityName="Route";
		details=new ArrayList<RouteDetail>();
	}

	public Route(Long id) {
		entityName="Route";
		this.id=id;
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

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
