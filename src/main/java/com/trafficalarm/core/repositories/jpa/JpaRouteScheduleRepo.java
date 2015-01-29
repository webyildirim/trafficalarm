package com.trafficalarm.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.structure.BaseEntity;
import com.structure.persistence.MainDao;
import com.trafficalarm.core.model.entities.RouteSchedule;
import com.trafficalarm.core.repositories.RouteScheduleRepo;

/**
 * Created by webyildirim on 7/10/14.
 */
@Repository
public class JpaRouteScheduleRepo implements RouteScheduleRepo{
    @PersistenceContext
    private EntityManager manager;

	@Autowired
    private MainDao dao=null;

    public JpaRouteScheduleRepo() {
    	dao=new MainDao();
	}
    
    @Override
    public RouteSchedule createRouteSchedule(RouteSchedule data) throws Exception {
        return (RouteSchedule) dao.saveOrUpdateEntity(manager, data);
    }

    @Override
    public RouteSchedule findRouteSchedule(String id) {
    	BaseEntity entity=new RouteSchedule();
    	entity.setId(id);    	
        return (RouteSchedule) dao.findByPrimaryKey(manager, entity);
    }

    @Override
    public RouteSchedule deleteRouteSchedule(String id) throws Exception {
    	RouteSchedule routeGroup=findRouteSchedule(id);
    	if(routeGroup!=null)
    		dao.removeEntity(manager, routeGroup);
        return routeGroup;
    }

    @Override
    public List<RouteSchedule> findByRouteGroup(String routeGroupId) {
        Query query = manager.createQuery("SELECT rg.routeSchedules FROM RouteGroup rg WHERE rg.id=?1");
        query.setParameter(1, routeGroupId);
        return query.getResultList();
    }
}
