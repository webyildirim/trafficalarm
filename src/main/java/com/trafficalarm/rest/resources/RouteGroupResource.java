package com.trafficalarm.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.trafficalarm.core.model.entities.RouteGroup;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteGroupResource extends ResourceSupport {

    private String title;

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

    public RouteGroup toRouteGroup() {
    	RouteGroup routeGroup = new RouteGroup();
    	routeGroup.setTitle(title);
        return routeGroup;
    }
}
