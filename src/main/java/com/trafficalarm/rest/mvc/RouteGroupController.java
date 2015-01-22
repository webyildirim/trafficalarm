package com.trafficalarm.rest.mvc;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.model.entities.RouteSchedule;
import com.trafficalarm.core.services.RouteSchedService;
import com.trafficalarm.core.services.RouteGroupService;
import com.trafficalarm.core.services.RouteService;
import com.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.trafficalarm.core.services.util.RouteSchedList;
import com.trafficalarm.core.services.util.RouteList;
import com.trafficalarm.rest.exceptions.NotFoundException;
import com.trafficalarm.rest.resources.RouteGroupResource;
import com.trafficalarm.rest.resources.RouteSchedListResource;
import com.trafficalarm.rest.resources.RouteSchedResource;
import com.trafficalarm.rest.resources.RouteListResource;
import com.trafficalarm.rest.resources.RouteResource;
import com.trafficalarm.rest.resources.asm.RouteGroupResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteSchedListResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteSchedResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteListResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteResourceAsm;

/**
 * Created by webyildirim on 6/28/14.
 */
@Controller
@RequestMapping("/rest/route-groups")
public class RouteGroupController {
    private RouteGroupService routeGroupService;
    private RouteService routeService;
    private RouteSchedService routeSchedService;

    @Autowired
    public RouteGroupController(RouteGroupService routeGroupService, RouteService routeService, RouteSchedService routeSchedService) {
        this.routeGroupService = routeGroupService;
        this.routeService=routeService;
        this.routeSchedService=routeSchedService;
    }
    
    @RequestMapping(value="/{routeGroupId}",
        method = RequestMethod.GET)
    public ResponseEntity<RouteGroupResource> getRouteGroupResource(@PathVariable Long routeGroupId)
    {
        RouteGroup routeGroup = routeGroupService.findRouteGroup(routeGroupId);
        if(routeGroup != null) {
            RouteGroupResource res = new RouteGroupResourceAsm().toResource(routeGroup);
            return new ResponseEntity<RouteGroupResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<RouteGroupResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{routeGroupId}/routes",
            method = RequestMethod.POST)
    public ResponseEntity<RouteResource> createRoute(
            @PathVariable Long routeGroupId,
            @RequestBody RouteResource sentResource
    ) {
        Route createdEntity = null;
        try {
            createdEntity = routeService.createRoute(routeGroupId, sentResource.toRoute());
            RouteResource createdResource = new RouteResourceAsm().toResource(createdEntity);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(createdResource.getLink("self").getHref()));
            return new ResponseEntity<RouteResource>(createdResource, headers, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
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

    @RequestMapping(value="/{routeGroupId}/route-schedules",
            method = RequestMethod.GET)
    public ResponseEntity<RouteSchedListResource> findAllSchedules(
            @PathVariable Long routeGroupId)
    {
        try {
            RouteSchedList list = routeGroupService.findAllRouteSchedules(routeGroupId);
            RouteSchedListResource res = new RouteSchedListResourceAsm().toResource(list);
            return new ResponseEntity<RouteSchedListResource>(res, HttpStatus.OK);
        } catch(EntityNotFoundException exception)
        {
            throw new NotFoundException(exception);
        }
    }

    @RequestMapping(value="/{routeGroupId}/route-schedules",
            method = RequestMethod.POST)
    public ResponseEntity<RouteSchedResource> createRouteSchedule(
            @PathVariable Long routeGroupId,
            @RequestBody RouteSchedResource sentResource
    ) {
        RouteSchedule createdEntity = null;
        try {
            createdEntity = routeSchedService.createRouteSched(routeGroupId, sentResource.toRouteSchedule());
            RouteSchedResource createdResource = new RouteSchedResourceAsm().toResource(createdEntity);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(createdResource.getLink("self").getHref()));
            return new ResponseEntity<RouteSchedResource>(createdResource, headers, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }
    
	@RequestMapping(value = "/{routeGroupId}", method = RequestMethod.PUT)
	public ResponseEntity<RouteGroupResource> updateRouteGroup(
			@PathVariable Long routeGroupId, @RequestBody RouteGroupResource sentResource) {
		RouteGroup updatedEntity = null;
		try {
			updatedEntity = routeGroupService.updateRouteGroup(routeGroupId,
					sentResource.toRouteGroup());

			if (updatedEntity != null) {
				RouteGroupResource res = new RouteGroupResourceAsm()
						.toResource(updatedEntity);
				return new ResponseEntity<RouteGroupResource>(res, HttpStatus.OK);
			} else {
				return new ResponseEntity<RouteGroupResource>(HttpStatus.NOT_FOUND);
			}
		} catch (EntityNotFoundException e) {
			throw new NotFoundException(e);
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}
}
