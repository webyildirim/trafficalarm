package com.trafficalarm.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.model.entities.RouteDetail;
import com.trafficalarm.rest.mvc.RouteController;
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
    public RouteDetailResource toResource(RouteDetail routeDetail) {
    	RouteDetailResource resource = new RouteDetailResource();
        resource.setTitle(routeDetail.getTitle());
        resource.setCoordinate(routeDetail.getCoordinate());
        resource.setRid(routeDetail.getId());
        
        resource.add(linkTo(RouteDetailController.class).slash(routeDetail.getId()).withSelfRel());
        if(routeDetail.getRoute() != null)
        {
            resource.add((linkTo(RouteController.class).slash(routeDetail.getRoute().getId()).withRel("route")));
        }
        return resource;
    }
}
