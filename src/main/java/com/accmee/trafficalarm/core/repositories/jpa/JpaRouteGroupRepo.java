package com.accmee.trafficalarm.core.repositories.jpa;

import org.springframework.stereotype.Repository;

import com.accmee.structure.BaseEntity;
import com.accmee.structure.BaseFilter;
import com.accmee.structure.persistence.MainDao;
import com.accmee.trafficalarm.core.models.entities.Account;
import com.accmee.trafficalarm.core.models.entities.BlogEntry;
import com.accmee.trafficalarm.core.models.entities.RouteGroup;
import com.accmee.trafficalarm.core.repositories.RouteGroupRepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.List;

/**
 * Created by webyildirim on 7/10/14.
 */
@Repository
public class JpaRouteGroupRepo implements RouteGroupRepo {
    @PersistenceContext
    private EntityManager manager;
    
    private MainDao dao=null;

    public JpaRouteGroupRepo() {
    	dao=new MainDao();
	}
    
    @Override
    public RouteGroup createRouteGroup(RouteGroup data) throws Exception {
        return (RouteGroup) dao.saveOrUpdateEntity(manager, data);
    }

    @Override
    public RouteGroup findRouteGroup(Long id) {
    	BaseEntity entity=new RouteGroup();
    	entity.setId(id);    	
        return (RouteGroup) dao.findByPrimaryKey(manager, entity);
    }

    @Override
    public RouteGroup findRouteGroupByFilter(BaseFilter baseFilter, String queryMethod) {
    	return (RouteGroup) dao.findObjectByFilter(manager, baseFilter);
    }

    @Override
    public List<RouteGroup> findRouteGroupsByAccount(Long accountId) {
        Query query = manager.createQuery("SELECT rg from RouteGroup gr where rg.owner.id=?1");
        query.setParameter(1, accountId);
        return query.getResultList();
    }

    @Override
    public RouteGroup deleteRouteGroup(Long id) throws Exception {
    	RouteGroup routeGroup=findRouteGroup(id);
    	if(routeGroup!=null)
    		dao.removeEntity(manager, routeGroup);
        return routeGroup;
    }
}
