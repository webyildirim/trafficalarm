package com.structure;


import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.structure.util.ObjectUtil;


@MappedSuperclass
public abstract class BaseEntityView extends SuperEntity
{
    //private transient Logger logger=Logger.getLogger(BaseEntity.class);

    protected Integer status = STATUS_ACTIVE;

    @Transient
    abstract public Integer getStatus();

    abstract public void setStatus(Integer Status);

    public final static int STATUS_ACTIVE = 0;
    public final static int STATUS_CANCELED = 1;
    //public static final Object[] args=new Object[0];
    //private transient Logger logger = Logger.getLogger(BaseEntity.class);

    @Transient
    public int hashCode()
    {
        return getPrimaryKeyValue().hashCode() + (getEntityName().hashCode() * 100);
    }

    @Transient
    public boolean equals(Object object)
    {
        if (!(object instanceof BaseEntityView))
            return false;

        try
        {
            BaseEntityView tempEntity = (BaseEntityView)object;
            if (ObjectUtil.isNull(tempEntity))
                return false;
            else
                return hashCode() == tempEntity.hashCode();
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
