package com.trafficalarm.rest.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.trafficalarm.core.model.entities.RouteDetail;
import com.trafficalarm.core.services.RouteDetailService;
import com.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.trafficalarm.rest.exceptions.NotFoundException;
import com.trafficalarm.rest.resources.RouteDetailResource;
import com.trafficalarm.rest.resources.asm.RouteDetailResourceAsm;

/**
 * Created by webyildirim on 6/28/14.
 */
@Controller
@RequestMapping("/rest/route-details")
public class RouteDetailController {
    private RouteDetailService routeDetailService;

    @Autowired
    public RouteDetailController(RouteDetailService routeDetailService) {
        this.routeDetailService=routeDetailService;
    }
    
    @RequestMapping(value="/{routeDetailId}",
        method = RequestMethod.GET)
    public ResponseEntity<RouteDetailResource> getRouteDetail(@PathVariable Long routeDetailId)
    {
        RouteDetail routeDetail = routeDetailService.findRouteDetail(routeDetailId);
        if(routeDetail != null) {
        	RouteDetailResource res = new RouteDetailResourceAsm().toResource(routeDetail);
            return new ResponseEntity<RouteDetailResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<RouteDetailResource>(HttpStatus.NOT_FOUND);
        }
    }

	@RequestMapping(value = "/{routeDetailId}", method = RequestMethod.DELETE)
	public void deleteRouteDetail(
			@PathVariable Long routeDetailId) {
		try {
			routeDetailService.deleteRouteDetail(routeDetailId);
		} catch (EntityNotFoundException exception) {
			throw new NotFoundException(exception);
		} catch (Exception e) {
			throw new RuntimeException(e.getCause());
		}
	}

}
