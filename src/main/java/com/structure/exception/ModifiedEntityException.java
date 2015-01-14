
package com.structure.exception;

import com.structure.SuperEntity;
import com.structure.exception.ApplicationException;
import com.structure.exception.InfrastructureException;


public class ModifiedEntityException extends InfrastructureException
{
    private SuperEntity modifiedEntity;
    private SuperEntity loadedEntity;

    public ModifiedEntityException(SuperEntity modifiedEntity, SuperEntity loadedEntity)
    {
        exceptionCode = "01";
        exceptionType = ApplicationException.EXCEPTION_TYPE_WARNING;
        setModifiedEntity(modifiedEntity);
        setLoadedEntity(loadedEntity);
    }

    public SuperEntity getModifiedEntity()
    {
        return modifiedEntity;
    }

    public void setModifiedEntity(SuperEntity modifiedEntity)
    {
        this.modifiedEntity = modifiedEntity;
    }

    public SuperEntity getLoadedEntity()
    {
        return loadedEntity;
    }

    public void setLoadedEntity(SuperEntity loadedEntity)
    {
        this.loadedEntity = loadedEntity;
    }

    public String getReason()
    {
        return modifiedEntity.toString();
    }
}
