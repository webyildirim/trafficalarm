package com.trafficalarm.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.model.entities.RouteSchedule;
import com.trafficalarm.rest.mvc.RouteSchedController;
import com.trafficalarm.rest.resources.RouteSchedResource;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteSchedResourceAsm extends ResourceAssemblerSupport<RouteSchedule, RouteSchedResource> {
    public RouteSchedResourceAsm() {
        super(RouteSchedController.class, RouteSchedResource.class);
    }

    @Override
    public RouteSchedResource toResource(RouteSchedule entity) {
    	RouteSchedResource resource = new RouteSchedResource	();
//        resource.setTitle(entity.getTitle());
//        resource.add(linkTo(Route.class).slash(entity.getId()).withSelfRel());
//        resource.add(linkTo(Route.class).slash(entity.getId()).slash("routes").withRel("routes"));
        resource.setRid(entity.getId());
        return resource;
    }
}
