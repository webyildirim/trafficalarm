package com.trafficalarm.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.rest.mvc.RouteController;
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
        resource.add(linkTo(Route.class).slash(route.getId()).withSelfRel());
        resource.add(linkTo(Route.class).slash(route.getId()).slash("routes").withRel("routes"));
        resource.setRid(route.getId());
        return resource;
    }
}
