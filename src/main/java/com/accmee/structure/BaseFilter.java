package com.accmee.structure;


import com.accmee.structure.util.ObjectUtil;
import com.accmee.structure.util.StringUtility;

import java.io.Serializable;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;


public abstract class BaseFilter implements Serializable
{
    protected Object object;

    protected BaseFilter(Object object)
    {
        this.object = object;
    }

    protected boolean isNull(Object object)
    {
        return ObjectUtil.isNull(object);
    }

    protected boolean isNullOrEmpty(String stringValue)
    {
        return StringUtility.isNullOrEmpty(stringValue);
    }
    
    protected boolean isNullOrEmpty(Collection list)
    {
        return ObjectUtil.isNullOrEmpty(list);
    }

    // method to check mandatory fields are supplied

    public void checkSearchFields() throws Exception
    {
        if (object == null)
            throw new Exception("MissingCriteriaException");
    }

    // default query excecution method

    abstract public Query createQuery(EntityManager manager);

    public Object getObject()
    {
        return this.object;
    }

}
