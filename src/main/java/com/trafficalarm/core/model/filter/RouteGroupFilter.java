package com.trafficalarm.core.model.filter;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.structure.BaseFilter;
import com.trafficalarm.core.model.entities.RouteGroup;

public class RouteGroupFilter  extends BaseFilter
{
    public RouteGroupFilter(Object object)
    {
        super(object);
    }

    @Override
    public Query createQuery(EntityManager manager)
    {
        RouteGroup routeGroup = (RouteGroup)object;
        StringBuilder queryString = new StringBuilder("\nFROM \n");
        queryString.append(routeGroup.getEntityName()).append(" rg WHERE 1=1\n");

        if (!isNullOrEmpty(routeGroup.getTitle()))
            queryString.append("AND lower(rg.title)=:title \n");
        if (!isNull(routeGroup.getOwner().getId()))
            queryString.append("AND rg.owner.id=:ownerId \n");
        
        Query query = manager.createQuery(queryString.toString());
        
        if (!isNullOrEmpty(routeGroup.getTitle()))
            query.setParameter("title", routeGroup.getTitle().toLowerCase());
        if (!isNull(routeGroup.getOwner()))
            query.setParameter("ownerId", routeGroup.getOwner().getId());
        
        return query;
    }
    
}