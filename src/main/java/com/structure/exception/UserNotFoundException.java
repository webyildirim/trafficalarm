package com.structure.exception;

import com.structure.exception.ApplicationException;
import com.structure.exception.InfrastructureException;

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
