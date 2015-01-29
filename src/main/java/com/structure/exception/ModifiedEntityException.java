
package com.structure.exception;

import com.structure.BaseEntity;


public class ModifiedEntityException extends InfrastructureException
{
    private BaseEntity modifiedEntity;
    private BaseEntity loadedEntity;

    public ModifiedEntityException(BaseEntity modifiedEntity, BaseEntity loadedEntity)
    {
        exceptionCode = "01";
        exceptionType = ApplicationException.EXCEPTION_TYPE_WARNING;
        setModifiedEntity(modifiedEntity);
        setLoadedEntity(loadedEntity);
    }

    public BaseEntity getModifiedEntity()
    {
        return modifiedEntity;
    }

    public void setModifiedEntity(BaseEntity modifiedEntity)
    {
        this.modifiedEntity = modifiedEntity;
    }

    public BaseEntity getLoadedEntity()
    {
        return loadedEntity;
    }

    public void setLoadedEntity(BaseEntity loadedEntity)
    {
        this.loadedEntity = loadedEntity;
    }

    public String getReason()
    {
        return modifiedEntity.toString();
    }
}
