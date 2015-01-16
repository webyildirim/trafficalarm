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
import com.trafficalarm.core.model.entities.RouteDetail;
import com.trafficalarm.core.services.RouteDetailService;
import com.trafficalarm.core.services.RouteService;
import com.trafficalarm.core.services.exceptions.EntityNotFoundException;
import com.trafficalarm.rest.exceptions.NotFoundException;
import com.trafficalarm.rest.resources.RouteDetailResource;
import com.trafficalarm.rest.resources.RouteResource;
import com.trafficalarm.rest.resources.asm.RouteDetailResourceAsm;
import com.trafficalarm.rest.resources.asm.RouteResourceAsm;

/**
 * Created by webyildirim on 6/28/14.
 */
@Controller
@RequestMapping("/rest/routes")
public class RouteGroupSchedController {

    private RouteService routeService;
    private RouteDetailService routeDetailService;

    @Autowired
    public RouteGroupSchedController(RouteService routeService, RouteDetailService routeDetailService) {
        this.routeService=routeService;
        this.routeDetailService=routeDetailService;
    }
    
    @RequestMapping(value="/{routeId}",
        method = RequestMethod.GET)
    public ResponseEntity<RouteResource> getRoute(@PathVariable Long routeId)
    {
        Route route = routeService.findRoute(routeId);
        if(route != null) {
            RouteResource res = new RouteResourceAsm().toResource(route);
            return new ResponseEntity<RouteResource>(res, HttpStatus.OK);
        } else {
            return new ResponseEntity<RouteResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value="/{routeId}/routeDetails",
            method = RequestMethod.POST)
    public ResponseEntity<RouteDetailResource> createRouteDetail(
            @PathVariable Long routeId,
            @RequestBody RouteDetailResource sentResource
    ) {
        RouteDetail createdEntity = null;
        try {
            createdEntity = routeDetailService.createRouteDetail(routeId, sentResource.toRoute());
            RouteDetailResource createdResource = new RouteDetailResourceAsm().toResource(createdEntity);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(createdResource.getLink("self").getHref()));
            return new ResponseEntity<RouteDetailResource>(createdResource, headers, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @RequestMapping(value="/{routeId}/routeDetails")
//    public ResponseEntity<RouteDetailListResource> findAllRouteDetails(
//            @PathVariable Long routeId)
//    {
//        try {
//            RouteDetailList list = routeService.findAllRouteDetails(routeId);
//            RouteDetailListResource res = new RouteDetailListResourceAsm().toResource(list);
//            return new ResponseEntity<RouteDetailListResource>(res, HttpStatus.OK);
//        } catch(EntityNotFoundException exception)
//        {
//            throw new NotFoundException(exception);
//        }
//    }

}
