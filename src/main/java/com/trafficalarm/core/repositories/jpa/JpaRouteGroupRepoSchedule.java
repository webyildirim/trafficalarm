package com.trafficalarm.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.structure.BaseEntity;
import com.structure.persistence.MainDao;
import com.trafficalarm.core.model.entities.RouteGroupSchedule;
import com.trafficalarm.core.repositories.RouteGroupScheduleRepo;

/**
 * Created by webyildirim on 7/10/14.
 */
@Repository
public class JpaRouteGroupRepoSchedule implements RouteGroupScheduleRepo{
    @PersistenceContext
    private EntityManager manager;
    
    private MainDao dao=null;

    public JpaRouteGroupRepoSchedule() {
    	dao=new MainDao();
	}
    
    @Override
    public RouteGroupSchedule createRouteGroupSchedule(RouteGroupSchedule data) throws Exception {
        return (RouteGroupSchedule) dao.saveOrUpdateEntity(manager, data);
    }

    @Override
    public RouteGroupSchedule findRouteGroupSchedule(Long id) {
    	BaseEntity entity=new RouteGroupSchedule();
    	entity.setId(id);    	
        return (RouteGroupSchedule) dao.findByPrimaryKey(manager, entity);
    }

    @Override
    public RouteGroupSchedule deleteRouteGroupSchedule(Long id) throws Exception {
    	RouteGroupSchedule routeGroup=findRouteGroupSchedule(id);
    	if(routeGroup!=null)
    		dao.removeEntity(manager, routeGroup);
        return routeGroup;
    }

    @Override
    public List<RouteGroupSchedule> findByRouteGroup(Long routeGroupId) {
        Query query = manager.createQuery("SELECT rg.routeSchedules FROM RouteGroup rg WHERE rg.id=?1");
        query.setParameter(1, routeGroupId);
        return query.getResultList();
    }
}
