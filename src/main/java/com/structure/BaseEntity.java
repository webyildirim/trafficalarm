package com.structure;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@MappedSuperclass
public abstract class BaseEntity extends SuperEntity
{
    //private transient Logger logger=Logger.getLogger(BaseEntity.class);
    protected int version;
    protected Date updateDate;
    //protected Integer updateUserId;
    protected String updateUserName = "";
    protected int status = STATUS_ACTIVE;

    public final static int STATUS_ACTIVE = 0;
    public final static int STATUS_CANCELED = 1;
    //public static final Object[] args=new Object[0];
    //private transient Logger logger = Logger.getLogger(BaseEntity.class);

    @Transient
    public String getEntityName()
    {
        return entityName;
    }

    @Transient
    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    //if db is other than oracle remove columnDefinition

    @Column(name = "STATUS", nullable = false, precision = 1, scale = 0, columnDefinition = "number(1,0)")
    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    //if db is other than oracle remove columnDefinition

    @Column(name = "VERSION", nullable = false, precision = 3, scale = 0, columnDefinition = "number(3,0)")
    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    //    @Column(name = "UPDATEUSERID")
    //    public Integer getUpdateUserId()
    //    {
    //        return updateUserId;
    //    }
    //
    //    public void setUpdateUserId(Integer updateUserId)
    //    {
    //        this.updateUserId = updateUserId;
    //    }

    @Column(name = "UPDATEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateDate()
    {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate)
    {
        this.updateDate = updateDate;
    }

    public void setUpdateUserName(String updateUserName)
    {
        this.updateUserName = updateUserName;
    }

    @Column(name = "UPDATEUSERNAME", length = 32)
    public String getUpdateUserName()
    {
        return updateUserName;
    }

    @Transient
    public boolean isCancelled()
    {
        return STATUS_CANCELED == getStatus() ? true : false;
    }
}

