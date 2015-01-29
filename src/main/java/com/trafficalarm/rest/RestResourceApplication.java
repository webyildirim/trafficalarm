package com.trafficalarm.rest;

import javax.ws.rs.container.ContainerResponseFilter;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.trafficalarm.rest.exceptions.AccessDeniedExceptionMapper;
import com.trafficalarm.rest.filter.jersey.JerseyCrossOriginResourceSharingFilter;
import com.trafficalarm.rest.mvc.resource.GenericExceptionMapper;

/**
 * Created by iainporter on 28/07/2014.
 */
public class RestResourceApplication extends ResourceConfig
{

	public RestResourceApplication()
	{

		packages("com.trafficalarm.rest.mvc.resource", "com.trafficalarm.rest.mvc");

		register(RequestContextFilter.class);

		ApplicationContext rootCtx = ContextLoader.getCurrentWebApplicationContext();
		ContainerResponseFilter filter = rootCtx.getBean(JerseyCrossOriginResourceSharingFilter.class);
		register(filter);

		register(GenericExceptionMapper.class);
		register(AccessDeniedExceptionMapper.class);

		register(JacksonFeature.class);

	}
}