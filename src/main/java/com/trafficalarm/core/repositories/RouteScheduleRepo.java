package com.trafficalarm.core.repositories;

import java.util.List;

import com.trafficalarm.core.model.entities.RouteSchedule;

/**
 * Created by webyildirim on 7/10/14.
 */
public interface RouteScheduleRepo {

	public RouteSchedule createRouteSchedule(RouteSchedule data) throws Exception;

	public RouteSchedule findRouteSchedule(Long id);

	public RouteSchedule deleteRouteSchedule(Long id)
			throws Exception;

	public List<RouteSchedule> findByRouteGroup(Long routeGroupId);
}
