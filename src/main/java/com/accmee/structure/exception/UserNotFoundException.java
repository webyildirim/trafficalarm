package com.accmee.structure.exception;

import com.accmee.structure.exception.ApplicationException;
import com.accmee.structure.exception.InfrastructureException;

public class UserNotFoundException extends InfrastructureException
{
    private String reason="";
    public UserNotFoundException()
    {
        exceptionType = ApplicationException.EXCEPTION_TYPE_ERROR;
        exceptionCode = "10";
    }

    public UserNotFoundException(String reason)
    {
        exceptionType = ApplicationException.EXCEPTION_TYPE_ERROR;
        exceptionCode = "10";
        
        this.reason=reason;
    }

    @Override
    public String getReason()
    {
        return reason;
    }
}
