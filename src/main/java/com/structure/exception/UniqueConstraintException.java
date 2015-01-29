package com.structure.exception;


public class UniqueConstraintException extends InfrastructureException
{
    private Exception reasonException;
    private String reason="";
    
    public UniqueConstraintException()
    {
        exceptionType = ApplicationException.EXCEPTION_TYPE_ERROR;
        exceptionCode = "06";
    }
    
    public UniqueConstraintException(Exception exception)
    {
        this(exception, "");
    }
    
    public UniqueConstraintException(String reason)
    {
        this(null, reason);
    }
    
    public UniqueConstraintException(Exception exception, String reason)
    {
        exceptionType = ApplicationException.EXCEPTION_TYPE_ERROR;
        exceptionCode = "06";
        
        this.reasonException=exception;
        this.reason=reason;
    }

    @Override
    public String getReason()
    {
        return reasonException!=null?reasonException.toString():""+reason;
    }
}
