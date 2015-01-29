package com.trafficalarm.rest.mvc;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;


@Path("/rest/healthcheck")
@Component
@Produces({MediaType.TEXT_PLAIN})
public class HealthCheckResource {

    @Value("${application.version}")
    String version;


    @GET
    @PreAuthorize("permitAll")
    public Response ping() {
        return Response.ok().entity("Running version " + version).build();
    }
}
