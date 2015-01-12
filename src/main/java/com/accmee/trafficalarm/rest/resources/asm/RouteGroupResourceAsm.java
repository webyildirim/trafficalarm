package com.accmee.trafficalarm.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.accmee.trafficalarm.core.models.entities.RouteGroup;
import com.accmee.trafficalarm.rest.mvc.AccountController;
import com.accmee.trafficalarm.rest.mvc.RouteGroupController;
import com.accmee.trafficalarm.rest.resources.RouteGroupResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

/**
 * Created by Chris on 6/30/14.
 */
public class RouteGroupResourceAsm extends ResourceAssemblerSupport<RouteGroup, RouteGroupResource> {
    public RouteGroupResourceAsm() {
        super(RouteGroupController.class, RouteGroupResource.class);
    }

    @Override
    public RouteGroupResource toResource(RouteGroup routeGroup) {
    	RouteGroupResource resource = new RouteGroupResource();
        resource.setTitle(routeGroup.getTitle());
        resource.add(linkTo(RouteGroup.class).slash(routeGroup.getId()).withSelfRel());
        resource.add(linkTo(RouteGroup.class).slash(routeGroup.getId()).slash("routes").withRel("routes"));
        resource.setRid(routeGroup.getId());
        if(routeGroup.getOwner() != null)
            resource.add(linkTo(AccountController.class).slash(routeGroup.getOwner().getId()).withRel("owner"));
        return resource;
    }
}
