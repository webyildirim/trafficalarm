package com.trafficalarm.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.services.util.RouteDetailList;
import com.trafficalarm.rest.mvc.RouteController;
import com.trafficalarm.rest.resources.RouteDetailListResource;
import com.trafficalarm.rest.resources.RouteDetailResource;

/**
 * Created by webyildirim
 */
public class RouteDetailListResourceAsm extends ResourceAssemblerSupport<RouteDetailList, RouteDetailListResource> {
    public RouteDetailListResourceAsm() {
        super(RouteController.class, RouteDetailListResource.class);
    }

    @Override
    public RouteDetailListResource toResource(RouteDetailList list) {
        List<RouteDetailResource> resources = new RouteDetailResourceAsm().toResources(list.getDetails());
        RouteDetailListResource listResource = new RouteDetailListResource();
        listResource.setRouteDetails(resources);
        listResource.add(linkTo(methodOn(RouteController.class).findDetailsByRoute(list.getRouteId())).withSelfRel());
        return listResource;
    }
}
