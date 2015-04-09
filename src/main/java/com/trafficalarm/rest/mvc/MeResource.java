package com.trafficalarm.rest.mvc;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.springframework.stereotype.Component;

import com.trafficalarm.core.model.entities.Account;
import com.trafficalarm.rest.api.user.ApiUser;
import com.trafficalarm.rest.exceptions.user.UserNotFoundException;
import com.trafficalarm.rest.mvc.resource.BaseResource;

/**
 * @version 1.0
 * @author: Iain Porter
 * @since 31/05/2013
 */
@Path("/rest/me")
@Component
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class MeResource extends BaseResource {

    @RolesAllowed({"ROLE_USER"})
    @GET
    public ApiUser getUser(final @Context SecurityContext securityContext) {
        Account requestingUser = loadUserFromSecurityContext(securityContext);
        if(requestingUser == null) {
            throw new UserNotFoundException();
        }
        return new ApiUser(requestingUser);
    }
    
    /**
    @RequestMapping(value = "/check-guest", method = RequestMethod.GET)
    @RolesAllowed({"ROLE_GUEST"})
    @GET
    public Response checkMyGuestAuth(@Context SecurityContext sc) {
        Account user = loadUserFromSecurityContext(sc);
        return Response.ok().entity("{\"message\":\"" + user.getName() + " is authorized to access\"}").build();
    }*/
}
