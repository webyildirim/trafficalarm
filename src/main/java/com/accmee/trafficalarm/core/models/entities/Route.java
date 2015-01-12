package com.accmee.trafficalarm.core.models.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.accmee.structure.BaseEntity;

/**
 * Created by webyildirim on 6/28/14.
 */
@Entity
public class Route extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    
    @OneToMany(targetEntity = RouteDetail.class, cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
    @JoinColumn(name = "ROUTEID")
    private Collection<RouteDetail> routeDeails;

	public Route() {
		entityName="Route";
		routeDeails=new ArrayList<RouteDetail>();
	}

    public Collection<RouteDetail> getRouteDeails() {
		return routeDeails;
	}

	public void setRouteDeails(Collection<RouteDetail> routeDeails) {
		this.routeDeails = routeDeails;
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
