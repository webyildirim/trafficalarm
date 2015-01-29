package com.structure.exception;

import com.structure.BaseEntity;

public class CascadeRemovedEntityException extends InfrastructureException
{
    private BaseEntity removedEntity;

    public CascadeRemovedEntityException(BaseEntity removedEntity)
    {
        exceptionCode = "04";
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
