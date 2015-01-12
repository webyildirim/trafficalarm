package com.accmee.structure;


import com.accmee.structure.util.DateUtility;

import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;


public class BaseEntityListener
{
    public static final int ENTITY_STATUS_PERSISTED = 1;
    public static final int ENTITY_STATUS_UPDATED = 2;
    public static final int ENTITY_STATUS_REMOVED = 3;

    protected void sendQueueMessage(BaseEntity baseEntity, String destination, int status)
    {
    }

    @PrePersist
    public void prePersist(Object object) throws NamingException
    {
        BaseEntity baseEntity = (BaseEntity)object;
        baseEntity.setUpdateDate(DateUtility.getToday());
        //baseEntity.setCreateDate(new Date());
        try
        {
            InitialContext ic = new InitialContext();
            //SessionContext ctx = (SessionContext)ic.lookup("java:comp/EJBContext");
            //baseEntity.setUpdateUserName(ctx.getCallerPrincipal().getName());
            //baseEntity.setStatus(0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @PreUpdate
    public void preUpdate(Object object) throws NamingException
    {
        BaseEntity baseEntity = (BaseEntity)object;
        baseEntity.setUpdateDate(DateUtility.getToday());
        try
        {
            InitialContext ic = new InitialContext();
            //SessionContext ctx = (SessionContext)ic.lookup("java:comp/EJBContext");
            //baseEntity.setUpdateUserName(ctx.getCallerPrincipal().getName());
            //baseEntity.setStatus(0);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //    @PostLoad
    //    public void postLoad(Object object)
    //    {
    //
    //        BaseEntity baseEntity = (BaseEntity)object;
    //        baseEntity.setUpdateDate(new Date());
    //        try
    //        {
    //            InitialContext ic = new InitialContext();
    //            SessionContext ctx = (SessionContext)ic.lookup("java:comp/EJBContext");
    //            baseEntity.setStatus(baseEntity.getStatus());
    //
    //        }
    //        catch (Exception e)
    //        {
    //
    //        }
    //    }
}
