/*
 * This filter provides CORS support allowing resources to be requested from other domains
 * This is important if your client and your server do not reside on the same server or are running on different ports
 */
package com.trafficalarm.rest.filter.jersey;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.trafficalarm.rest.filter.BaseCORSFilter;

@Component
public class JerseyCrossOriginResourceSharingFilter extends BaseCORSFilter implements ContainerResponseFilter {

    @Value("${cors.allowed.origins}")
    String allowedOriginsString;

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if(requestContext.getHeaders().containsKey("Origin")) {
            String origin = requestContext.getHeaders().getFirst("Origin");
            if(getAllowedOrigins(allowedOriginsString).contains(origin)) {
                responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
                responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
                responseContext.getHeaders().add("Access-Control-Allow-Headers", "X-HTTP-Method-Override, Content-Type, x-requested-with, Authorization");
                responseContext.getHeaders().add("Access-Control-Max-Age", "3600");
            }
        }
    }


}
