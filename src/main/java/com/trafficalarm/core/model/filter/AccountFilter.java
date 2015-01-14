package com.trafficalarm.core.model.filter;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.structure.BaseFilter;
import com.trafficalarm.core.model.entities.Account;

public class AccountFilter  extends BaseFilter
{
    public AccountFilter(Object object)
    {
        super(object);
    }

    @Override
    public Query createQuery(EntityManager manager)
    {
        Account user = (Account)object;
        StringBuilder queryString = new StringBuilder("\nFROM \n");
        queryString.append(user.getEntityName()).append(" u WHERE 1=1\n");

        if (!isNullOrEmpty(user.getFirstName()))
            queryString.append("AND lower(u.firstName) like :firstName \n");
        if (!isNullOrEmpty(user.getMiddleName()))
            queryString.append("AND lower(u.middleName) like :middleName \n");
        if (!isNullOrEmpty(user.getLastName()))
            queryString.append("AND lower(u.lastName) like :lastName \n");
        if (!isNullOrEmpty(user.getName()))
            queryString.append("AND lower(u.name) like :name \n");
        
        Query query = manager.createQuery(queryString.toString());
        
        if (!isNullOrEmpty(user.getFirstName()))
            query.setParameter("firstName", user.getFirstName().toLowerCase());
        if (!isNullOrEmpty(user.getMiddleName()))
            query.setParameter("middleName", user.getMiddleName().toLowerCase());
        if (!isNullOrEmpty(user.getLastName()))
            query.setParameter("lastName", user.getLastName().toLowerCase());
        if (!isNullOrEmpty(user.getName()))
            query.setParameter("name", user.getName().toLowerCase());
        
        return query;
    }
    
}