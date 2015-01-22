package com.trafficalarm.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.services.util.RouteList;
import com.trafficalarm.rest.mvc.RouteGroupController;
import com.trafficalarm.rest.resources.RouteListResource;
import com.trafficalarm.rest.resources.RouteResource;

/**
 * Created by webyildirim
 */
public class RouteListResourceAsm extends ResourceAssemblerSupport<RouteList, RouteListResource> {
    public RouteListResourceAsm() {
        super(RouteGroupController.class, RouteListResource.class);
    }

    @Override
    public RouteListResource toResource(RouteList list) {
        List<RouteResource> resources = new RouteResourceAsm().toResources(list.getEntities());
        RouteListResource listResource = new RouteListResource();
        listResource.setRoutes(resources);
        listResource.add(linkTo(methodOn(RouteGroupController.class).findAllRoutes(list.getRouteGroupId())).withSelfRel());
        return listResource;
    }
}
