package com.trafficalarm.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.model.entities.RouteGroupSchedule;
import com.trafficalarm.rest.mvc.RouteGroupSchedController;
import com.trafficalarm.rest.resources.RouteGroupSchedResource;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteGroupSchedResourceAsm extends ResourceAssemblerSupport<RouteGroupSchedule, RouteGroupSchedResource> {
    public RouteGroupSchedResourceAsm() {
        super(RouteGroupSchedController.class, RouteGroupSchedResource.class);
    }

    @Override
    public RouteGroupSchedResource toResource(RouteGroupSchedule entity) {
    	RouteGroupSchedResource resource = new RouteGroupSchedResource	();
//        resource.setTitle(entity.getTitle());
//        resource.add(linkTo(Route.class).slash(entity.getId()).withSelfRel());
//        resource.add(linkTo(Route.class).slash(entity.getId()).slash("routes").withRel("routes"));
        resource.setRid(entity.getId());
        return resource;
    }
}
