package com.trafficalarm.rest.resources.asm;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.services.util.RouteSchedList;
import com.trafficalarm.rest.mvc.RouteSchedController;
import com.trafficalarm.rest.resources.RouteSchedListResource;
import com.trafficalarm.rest.resources.RouteSchedResource;

/**
 * Created by webyildirim on 6/30/14.
 */
public class RouteSchedListResourceAsm extends ResourceAssemblerSupport<RouteSchedList, RouteSchedListResource> {
    public RouteSchedListResourceAsm() {
        super(RouteSchedController.class, RouteSchedListResource.class);
    }

    @Override
    public RouteSchedListResource toResource(RouteSchedList list) {
        List<RouteSchedResource> resources = new RouteSchedResourceAsm().toResources(list.getRouteSchedules());
        RouteSchedListResource listResource = new RouteSchedListResource();
        listResource.setSchedules(resources);
//        resource.setTitle(entity.getTitle());
//        resource.add(linkTo(Route.class).slash(entity.getId()).withSelfRel());
//        resource.add(linkTo(Route.class).slash(entity.getId()).slash("routes").withRel("routes"));
        return listResource;
    }
}
