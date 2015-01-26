package com.trafficalarm.rest.mvc;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.model.entities.RouteDetail;
import com.trafficalarm.core.services.RouteDetailService;
import com.trafficalarm.core.services.RouteService;
import com.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.trafficalarm.core.services.util.RouteDetailList;
import com.trafficalarm.rest.exceptions.NotFoundException;
import com.trafficalarm.rest.resources.RouteDetailListResource;
import com.trafficalarm.rest.resources.RouteDetailResource;
import com.trafficalarm.rest.resources.RouteResource;
import com.trafficalarm.rest.resources.asm.RouteDetailListResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteDetailResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteResourceAsm;

/**
 * Created by webyildirim on 6/28/14.
 */
@Controller
@RequestMapping("/rest/routes")
public class RouteController {

	private RouteService routeService;
	private RouteDetailService routeDetailService;

	@Autowired
	public RouteController(RouteService routeService,
			RouteDetailService routeDetailService) {
		this.routeService = routeService;
		this.routeDetailService = routeDetailService;
	}

	@RequestMapping(value = "/{routeId}", method = RequestMethod.GET)
	public ResponseEntity<RouteResource> getRoute(@PathVariable Long routeId) {
		Route route = routeService.findRoute(routeId);
		if (route != null) {
			RouteResource res = new RouteResourceAsm().toResource(route);
			return new ResponseEntity<RouteResource>(res, HttpStatus.OK);
		} else {
			return new ResponseEntity<RouteResource>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{routeId}/route-details", method = RequestMethod.POST)
	public ResponseEntity<RouteDetailResource> createRouteDetail(
			@PathVariable Long routeId,
			@RequestBody RouteDetailResource sentResource) {
		RouteDetail createdEntity = null;
		try {
			createdEntity = routeDetailService.createRouteDetail(routeId,
					sentResource.toRoute());
			RouteDetailResource createdResource = new RouteDetailResourceAsm()
					.toResource(createdEntity);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdResource.getLink("self")
					.getHref()));
			return new ResponseEntity<RouteDetailResource>(createdResource,
					headers, HttpStatus.CREATED);
		} catch (EntityNotFoundException e) {
			throw new NotFoundException(e);
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	@RequestMapping(value = "/{routeId}", method = RequestMethod.PUT)
	public ResponseEntity<RouteResource> updateRoute(
			@PathVariable Long entityId, @RequestBody RouteResource sentResource) {
		Route updatedEntity = null;
		try {
			updatedEntity = routeService.updateRoute(entityId,
					sentResource.toRoute());

			if (updatedEntity != null) {
				RouteResource res = new RouteResourceAsm()
						.toResource(updatedEntity);
				return new ResponseEntity<RouteResource>(res, HttpStatus.OK);
			} else {
				return new ResponseEntity<RouteResource>(HttpStatus.NOT_FOUND);
			}
		} catch (EntityNotFoundException e) {
			throw new NotFoundException(e);
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	@RequestMapping(value = "/{routeId}/route-details", method = RequestMethod.GET)
	public ResponseEntity<RouteDetailListResource> findDetailsByRoute(
			@PathVariable Long routeId) {
		try {
			RouteDetailList list = routeService.findDetailsByRoute(routeId);
			RouteDetailListResource res = new RouteDetailListResourceAsm()
					.toResource(list);
			return new ResponseEntity<RouteDetailListResource>(res,
					HttpStatus.OK);
		} catch (EntityNotFoundException exception) {
			throw new NotFoundException(exception);
		}
	}

	@RequestMapping(value = "/{routeId}", method = RequestMethod.DELETE)
	public void deleteRouteDetail(
			@PathVariable Long routeId) {
		try {
			routeService.deleteRoute(routeId);
		} catch (EntityNotFoundException exception) {
			throw new NotFoundException(exception);
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

	// @ExceptionHandler(EntityNotFoundException.class)
	// public ResponseEntity<Error> bookNotFound(EntityNotFoundException e) {
	// Error error = new Error("Entity with ID " + e.getEntityId() +
	// " not found");
	// return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	// }

}
