package com.trafficalarm.rest.mvc;

import java.net.URI;

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

import com.trafficalarm.core.model.entities.RouteSchedule;
import com.trafficalarm.core.services.RouteSchedService;
import com.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.trafficalarm.rest.exceptions.NotFoundException;
import com.trafficalarm.rest.resources.RouteSchedResource;
import com.trafficalarm.rest.resources.asm.RouteSchedResourceAsm;

/**
 * Created by webyildirim on 6/28/14.
 */
@Controller
@RequestMapping("/rest/route-schedules")
public class RouteSchedController
{

	private RouteSchedService routeSchedService;

	@Autowired
	public RouteSchedController(RouteSchedService routeSchedService)
	{
		this.routeSchedService = routeSchedService;
	}

	@RequestMapping(value = "/{routeSchedId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<RouteSchedResource> getRouteSched(@PathVariable String routeSchedId)
	{
		RouteSchedule groupSchedule = routeSchedService.findRouteSched(routeSchedId);
		if (groupSchedule != null)
		{
			RouteSchedResource res = new RouteSchedResourceAsm().toResource(groupSchedule);
			return new ResponseEntity<RouteSchedResource>(res, HttpStatus.OK);
		} else
		{
			return new ResponseEntity<RouteSchedResource>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{routeId}/route-schedules", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<RouteSchedResource> createRouteSched(@PathVariable String routeGroupId, @RequestBody RouteSchedResource sentResource)
	{
		RouteSchedule createdEntity = null;
		try
		{
			createdEntity = routeSchedService.createRouteSched(routeGroupId, sentResource.toRouteSchedule());
			RouteSchedResource createdResource = new RouteSchedResourceAsm().toResource(createdEntity);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdResource.getLink("self").getHref()));
			return new ResponseEntity<RouteSchedResource>(createdResource, headers, HttpStatus.CREATED);
		} catch (EntityNotFoundException e)
		{
			throw new NotFoundException(e);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/{routeSchedId}", method = RequestMethod.DELETE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public void deleteRouteSched(@PathVariable String routeSchedId)
	{
		try
		{
			routeSchedService.deleteRouteSched(routeSchedId);
		} catch (EntityNotFoundException exception)
		{
			throw new NotFoundException(exception);
		} catch (Exception e)
		{
			throw new RuntimeException(e.getCause());
		}
	}

}
