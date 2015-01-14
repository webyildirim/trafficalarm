package com.trafficalarm.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.trafficalarm.core.model.entities.Route;

/**
 * Created by webyildirim on 6/27/14.
 */
public class RouteResource extends ResourceSupport {
    private String title;

    private Long rid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Route toRoute() {
    	Route entity = new Route();
        entity.setTitle(title);
        return entity;
    }
}
