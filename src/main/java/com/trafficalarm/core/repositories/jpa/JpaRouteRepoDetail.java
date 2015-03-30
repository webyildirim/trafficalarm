package com.trafficalarm.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.structure.BaseEntity;
import com.structure.persistence.MainDao;
import com.trafficalarm.core.model.entities.RouteDetail;
import com.trafficalarm.core.repositories.RouteDetailRepo;

/**
 * Created by webyildirim on 7/10/14.
 */
@Repository
public class JpaRouteRepoDetail implements RouteDetailRepo {
	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private MainDao dao;

	public JpaRouteRepoDetail() 
	{
		//dao = new MainDao();
	}

	@Override
	public RouteDetail findRouteDetail(String id) {
		BaseEntity entity = new RouteDetail();
		entity.setId(id);
		return (RouteDetail) dao.findByPrimaryKey(manager, entity);
	}

	@Override
	public RouteDetail deleteRouteDetail(String id) throws Exception {
		RouteDetail routeDetail = findRouteDetail(id);
		if (routeDetail != null)
			dao.removeEntity(manager, routeDetail);
		return routeDetail;
	}

	@Override
	public RouteDetail updateRouteDetail(String id, RouteDetail entity)
			throws Exception {
		entity.setId(id);
		entity = (RouteDetail) dao.saveOrUpdateEntity(manager, entity);
		return entity;
	}

	@Override
	public RouteDetail createRouteDetail(RouteDetail entity) throws Exception {
		return (RouteDetail) dao.saveOrUpdateEntity(manager, entity);
	}

	@Override
	public List<RouteDetail> findByRouteId(String routeId) {
		Query query = manager
				.createQuery("SELECT r.details FROM Route r WHERE r.id=?1");
		query.setParameter(1, routeId);
		return query.getResultList();
	}
}
