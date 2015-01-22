package com.trafficalarm.rest.resources.asm;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.trafficalarm.core.services.util.RouteGroupList;
import com.trafficalarm.rest.mvc.RouteGroupController;
import com.trafficalarm.rest.resources.RouteGroupListResource;
import com.trafficalarm.rest.resources.RouteGroupResource;

/**
 * Created by webyildirim
 */
public class RouteGroupListResourceAsm extends ResourceAssemblerSupport<RouteGroupList, RouteGroupListResource> {
    public RouteGroupListResourceAsm() {
        super(RouteGroupController.class, RouteGroupListResource.class);
    }

    @Override
    public RouteGroupListResource toResource(RouteGroupList list) {
        List<RouteGroupResource> resources = new RouteGroupResourceAsm().toResources(list.getRouteGroups());
        RouteGroupListResource listResource = new RouteGroupListResource();
        listResource.setRouteGroups(resources);
        return listResource;
    }
}
