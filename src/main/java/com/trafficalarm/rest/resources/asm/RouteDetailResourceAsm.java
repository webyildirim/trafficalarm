package com.trafficalarm.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.model.entities.RouteDetail;
import com.trafficalarm.rest.mvc.RouteDetailController;
import com.trafficalarm.rest.resources.RouteDetailResource;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteDetailResourceAsm extends ResourceAssemblerSupport<RouteDetail, RouteDetailResource> {
    public RouteDetailResourceAsm() {
        super(RouteDetailController.class, RouteDetailResource.class);
    }

    @Override
    public RouteDetailResource toResource(RouteDetail route) {
    	RouteDetailResource resource = new RouteDetailResource();
        resource.setTitle(route.getTitle());
        resource.setCoordinate(route.getCoordinate());
        resource.add(linkTo(Route.class).slash(route.getId()).withSelfRel());
        resource.add(linkTo(Route.class).slash(route.getId()).slash("routes").withRel("routes"));
        resource.setRid(route.getId());
        return resource;
    }
}
