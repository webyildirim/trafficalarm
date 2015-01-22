package com.trafficalarm.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.rest.mvc.RouteController;
import com.trafficalarm.rest.mvc.RouteGroupController;
import com.trafficalarm.rest.resources.RouteResource;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteResourceAsm extends ResourceAssemblerSupport<Route, RouteResource> {
    public RouteResourceAsm() {
        super(RouteController.class, RouteResource.class);
    }

    @Override
    public RouteResource toResource(Route route) {
    	RouteResource resource = new RouteResource();
        resource.setTitle(route.getTitle());
        resource.setRid(route.getId());

        resource.add(linkTo(RouteController.class).slash(route.getId()).withSelfRel());
        resource.add(linkTo(RouteController.class).slash(route.getId()).slash("route-details").withRel("route-details"));
        if(route.getRouteGroup() != null)
        {
        	resource.add((linkTo(RouteGroupController.class).slash(route.getRouteGroup().getId()).withRel("route-group")));
        }
        return resource;
    }
}
