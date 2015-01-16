package com.trafficalarm.core.repositories;

import java.util.List;

import com.trafficalarm.core.model.entities.RouteGroupSchedule;

/**
 * Created by webyildirim on 7/10/14.
 */
public interface RouteGroupScheduleRepo {

	public RouteGroupSchedule createRouteGroupSchedule(RouteGroupSchedule data) throws Exception;

	public RouteGroupSchedule findRouteGroupSchedule(Long id);

	public RouteGroupSchedule deleteRouteGroupSchedule(Long id)
			throws Exception;

	public List<RouteGroupSchedule> findByRouteGroup(Long routeGroupId);
}
