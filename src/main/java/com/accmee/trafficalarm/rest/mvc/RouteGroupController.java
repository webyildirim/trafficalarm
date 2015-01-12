package com.accmee.trafficalarm.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.accmee.trafficalarm.core.models.entities.Blog;
import com.accmee.trafficalarm.core.models.entities.BlogEntry;
import com.accmee.trafficalarm.core.models.entities.Route;
import com.accmee.trafficalarm.core.models.entities.RouteGroup;
import com.accmee.trafficalarm.core.services.BlogService;
import com.accmee.trafficalarm.core.services.RouteGroupService;
import com.accmee.trafficalarm.core.services.RouteService;
import com.accmee.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.accmee.trafficalarm.core.services.util.BlogEntryList;
import com.accmee.trafficalarm.core.services.util.BlogList;
import com.accmee.trafficalarm.core.services.util.RouteList;
import com.accmee.trafficalarm.rest.exceptions.NotFoundException;
import com.accmee.trafficalarm.rest.resources.BlogEntryListResource;
import com.accmee.trafficalarm.rest.resources.BlogEntryResource;
import com.accmee.trafficalarm.rest.resources.BlogListResource;
import com.accmee.trafficalarm.rest.resources.BlogResource;
import com.accmee.trafficalarm.rest.resources.RouteGroupResource;
import com.accmee.trafficalarm.rest.resources.RouteListResource;
import com.accmee.trafficalarm.rest.resources.RouteResource;
import com.accmee.trafficalarm.rest.resources.asm.BlogEntryListResourceAsm;
import com.accmee.trafficalarm.rest.resources.asm.BlogEntryResourceAsm;
import com.accmee.trafficalarm.rest.resources.asm.BlogListResourceAsm;
import com.accmee.trafficalarm.rest.resources.asm.BlogResourceAsm;
import com.accmee.trafficalarm.rest.resources.asm.RouteGroupResourceAsm;

import java.net.URI;

/**
 * Created by webyildirim on 6/28/14.
 */
@Controller
@RequestMapping("/rest/route-groups")
public class RouteGroupController {
    private RouteGroupService routeGroupService;
    private RouteService routeService;

    @Autowired
    public RouteGroupController(RouteGroupService routeGroupService, RouteService routeService) {
        this.routeGroupService = routeGroupService;
        this.routeService=routeService;
    }
    
    @RequestMapping(value="/{routeGroupId}",
        method = RequestMethod.GET)
    public ResponseEntity<RouteGroupResource> getBlog(@PathVariable Long blogId)
    {
        RouteGroup routeGroup = routeGroupService.findRouteGroup(blogId);
        if(routeGroup != null) {
            RouteGroupResource res = new RouteGroupResourceAsm().toResource(routeGroup);
            return new ResponseEntity<RouteGroupResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<RouteGroupResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{routeGroupId}/routes",
            method = RequestMethod.POST)
    public ResponseEntity<RouteResource> createBlogEntry(
            @PathVariable Long routeGroupId,
            @RequestBody RouteResource sentResource
    ) {
        Route createdEntity = null;
        try {
            createdEntity = routeService.(routeGroupId, sentResource.toRoute());
            BlogEntryResource createdResource = new BlogEntryResourceAsm().toResource(createdEntity);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(createdResource.getLink("self").getHref()));
            return new ResponseEntity<BlogEntryResource>(createdResource, headers, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        }
    }

    @RequestMapping(value="/{routeGroupId}/routes")
    public ResponseEntity<RouteListResource> findAllRoutes(
            @PathVariable Long routeGroupId)
    {
        try {
            RouteList list = routeGroupService.findAllRoutes(routeGroupId);
            RouteListResource res = new RouteListResourceAsm().toResource(list);
            return new ResponseEntity<RouteListResource>(res, HttpStatus.OK);
        } catch(EntityNotFoundException exception)
        {
            throw new NotFoundException(exception);
        }
    }

}
