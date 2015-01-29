package com.trafficalarm.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.structure.BaseEntity;
import com.structure.BaseFilter;
import com.structure.persistence.MainDao;
import com.trafficalarm.core.model.entities.RouteGroup;
import com.trafficalarm.core.repositories.RouteGroupRepo;

/**
 * Created by webyildirim on 7/10/14.
 */
@Repository
public class JpaRouteGroupRepo implements RouteGroupRepo {
    @PersistenceContext
    private EntityManager manager;

	@Autowired
    private MainDao dao=null;

    public JpaRouteGroupRepo() {
    	dao=new MainDao();
	}
    
    @Override
    public RouteGroup createRouteGroup(RouteGroup data) throws Exception {
        return (RouteGroup) dao.saveOrUpdateEntity(manager, data);
    }

    @Override
    public RouteGroup updateRouteGroup(String id, RouteGroup entity) throws Exception {
    	entity.setId(id);
    	entity=(RouteGroup) dao.saveOrUpdateEntity(manager, entity);
        return entity;
    }

    @Override
    public RouteGroup findRouteGroup(String id) {
    	BaseEntity entity=new RouteGroup();
    	entity.setId(id);    	
        return (RouteGroup) dao.findByPrimaryKey(manager, entity);
    }

    @Override
    public RouteGroup findRouteGroupByFilter(BaseFilter baseFilter, String queryMethod) {
    	return (RouteGroup) dao.findObjectByFilter(manager, baseFilter);
    }

    @Override
    public List<RouteGroup> findRouteGroupsByAccount(String accountId) {
        Query query = manager.createQuery("SELECT rg from RouteGroup gr where rg.owner.id=?1");
        query.setParameter(1, accountId);
        return query.getResultList();
    }

    @Override
    public RouteGroup deleteRouteGroup(String id) throws Exception {
    	RouteGroup routeGroup=findRouteGroup(id);
    	if(routeGroup!=null)
    		dao.removeEntity(manager, routeGroup);
        return routeGroup;
    }
}
