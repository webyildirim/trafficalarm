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
import javax.persistence.OneToOne;

import com.accmee.structure.BaseEntity;

/**
 * Created by webyildirim on 6/28/14.
 */
@Entity
public class RouteGroup extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @OneToOne
    private Account owner;
    
    @OneToMany(targetEntity = Route.class, cascade = { CascadeType.ALL }, fetch=FetchType.EAGER)
    @JoinColumn(name = "ROUTEGROUPID")
    private Collection<Route> routes;

	public RouteGroup() {
		entityName="RouteGroup";
		routes=new ArrayList<Route>();
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

	@Override
	public String toString() {
		return getOwner().getName()+" "+getTitle();
	}
}
