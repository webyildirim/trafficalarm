package com.trafficalarm.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.services.util.RouteSchedList;
import com.trafficalarm.rest.mvc.RouteGroupController;
import com.trafficalarm.rest.resources.RouteSchedListResource;
import com.trafficalarm.rest.resources.RouteSchedResource;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteSchedListResourceAsm extends ResourceAssemblerSupport<RouteSchedList, RouteSchedListResource> {
    public RouteSchedListResourceAsm() {
        super(RouteGroupController.class, RouteSchedListResource.class);
    }

    @Override
    public RouteSchedListResource toResource(RouteSchedList list) {
        List<RouteSchedResource> resources = new RouteSchedResourceAsm().toResources(list.getRouteSchedules());
        RouteSchedListResource listResource = new RouteSchedListResource();
        listResource.setSchedules(resources);
        listResource.add(linkTo(methodOn(RouteGroupController.class).findAllSchedules(list.getRouteGroupId())).withSelfRel());
        return listResource;
    }
}
