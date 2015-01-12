package com.accmee.trafficalarm.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.accmee.trafficalarm.core.models.entities.RouteGroup;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteGroupResource extends ResourceSupport {

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

    public RouteGroup toRouteGroup() {
    	RouteGroup routeGroup = new RouteGroup();
    	routeGroup.setTitle(title);
        return routeGroup;
    }
}
