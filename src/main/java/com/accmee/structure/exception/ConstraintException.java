package com.accmee.structure.exception;

import com.accmee.structure.exception.ApplicationException;
import com.accmee.structure.exception.InfrastructureException;

public class ConstraintException extends InfrastructureException
{
    private String reason="";
    public ConstraintException()
    {
        exceptionType = ApplicationException.EXCEPTION_TYPE_ERROR;
        exceptionCode = "08";
    }

    public ConstraintException(String reason)
    {
        exceptionType = ApplicationException.EXCEPTION_TYPE_ERROR;
        exceptionCode = "08";
        
        this.reason=reason;
    }

    @Override
    public String getReason()
    {
        return reason;
    }
}
