package com.accmee.structure.exception;


import com.accmee.structure.exception.ApplicationException;
import com.accmee.structure.BaseEntity;
import com.accmee.structure.exception.InfrastructureException;


public class RemovedEntityException extends InfrastructureException
{
    private BaseEntity removedEntity;

    public RemovedEntityException(BaseEntity removedEntity)
    {
        exceptionCode = "02";
        exceptionType = ApplicationException.EXCEPTION_TYPE_WARNING;
        setRemovedEntity(removedEntity);
    }

    public BaseEntity getRemovedEntity()
    {
        return removedEntity;
    }

    public void setRemovedEntity(BaseEntity removedEntity)
    {
        this.removedEntity = removedEntity;
    }

    public String getReason()
    {
        return removedEntity.toString();
    }
}
