package com.structure.exception;


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
