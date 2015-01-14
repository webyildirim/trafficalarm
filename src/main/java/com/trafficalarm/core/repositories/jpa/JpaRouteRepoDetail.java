package com.trafficalarm.core.repositories.jpa;

import org.springframework.stereotype.Repository;

import com.structure.BaseEntity;
import com.structure.persistence.MainDao;
import com.trafficalarm.core.model.entities.Route;
import com.trafficalarm.core.model.entities.RouteDetail;
import com.trafficalarm.core.repositories.RouteDetailRepo;
import com.trafficalarm.core.repositories.RouteRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

/**
 * Created by webyildirim on 7/10/14.
 */
@Repository
public class JpaRouteRepoDetail implements RouteDetailRepo {
    @PersistenceContext
    private EntityManager manager;
    
    private MainDao dao=null;

    public JpaRouteRepoDetail() {
    	dao=new MainDao();
	}
    

    @Override
    public RouteDetail findRouteDetail(Long id) {
    	BaseEntity entity=new RouteDetail();
    	entity.setId(id);    	
        return (RouteDetail) dao.findByPrimaryKey(manager, entity);
    }

    @Override
    public RouteDetail deleteRouteDetail(Long id) throws Exception {
    	RouteDetail route=findRouteDetail(id);
    	dao.removeEntity(manager, route);
        return route;
    }

    @Override
    public RouteDetail updateRouteDetail(Long id, RouteDetail entity) throws Exception {
    	entity.setId(id);
    	entity=(RouteDetail) dao.saveOrUpdateEntity(manager, entity);
        return entity;
    }

    @Override
    public RouteDetail createRouteDetail(RouteDetail entity) throws Exception {
        return (RouteDetail) dao.saveOrUpdateEntity(manager, entity);
    }

    @Override
    public List<RouteDetail> findByRouteId(Long routeId) {
        Query query = manager.createQuery("SELECT r.details FROM Route r WHERE r.id=?1");
        query.setParameter(1, routeId);
        return query.getResultList();
    }
}
