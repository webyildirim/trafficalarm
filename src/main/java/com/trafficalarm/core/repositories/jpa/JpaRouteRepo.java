package com.trafficalarm.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.structure.BaseEntity;
import com.structure.persistence.MainDao;
import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.repositories.RouteRepo;

/**
 * Created by webyildirim on 7/10/14.
 */
@Repository
public class JpaRouteRepo implements RouteRepo {
    @PersistenceContext
    private EntityManager manager;

	@Autowired
    private MainDao dao=null;

    public JpaRouteRepo() {
    	dao=new MainDao();
	}
    

    @Override
    public Route findRoute(String id) {
    	BaseEntity entity=new Route();
    	entity.setId(id);    	
        return (Route) dao.findByPrimaryKey(manager, entity);
    }

    @Override
    public Route deleteRoute(String id) throws Exception {
    	Route route=findRoute(id);
    	dao.removeEntity(manager, route);
        return route;
    }

    @Override
    public Route updateRoute(String id, Route entity) throws Exception {
    	entity.setId(id);
    	entity=(Route) dao.saveOrUpdateEntity(manager, entity);
        return entity;
    }

    @Override
    public Route createRoute(Route entity) throws Exception {
        return (Route) dao.saveOrUpdateEntity(manager, entity);
    }

    @Override
    public List<Route> findByRouteGroup(String routeGroupId) {
        Query query = manager.createQuery("SELECT rg.routes FROM RouteGroup rg WHERE rg.id=?1");
        query.setParameter(1, routeGroupId);
        return query.getResultList();
    }
}
