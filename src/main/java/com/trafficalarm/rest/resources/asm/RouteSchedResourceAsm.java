package com.trafficalarm.rest.resources.asm;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.model.entities.RouteSchedule;
import com.trafficalarm.rest.mvc.RouteGroupController;
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
    	RouteSchedResource resource = new RouteSchedResource();
        resource.setCronExpression(entity.getCronExpression());
        resource.setTime(entity.getTime());
        resource.setMonApplied(entity.isMonApplied());
        resource.setTueApplied(entity.isTueApplied());
        resource.setWedApplied(entity.isWedApplied());
        resource.setThuApplied(entity.isThuApplied());
        resource.setFriApplied(entity.isFriApplied());
        resource.setSatApplied(entity.isSatApplied());
        resource.setSunApplied(entity.isSunApplied());
        resource.setRid(entity.getId());
        
        resource.add(linkTo(RouteSchedController.class).slash(entity.getId()).withSelfRel());
        if(entity.getRouteGroup()!= null)
        {
            resource.add((linkTo(RouteGroupController.class).slash(entity.getRouteGroup().getId()).withRel("route-group")));
        }
        return resource;
    }
}
