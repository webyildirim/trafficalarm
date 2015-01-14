package com.structure.exception;

public abstract class ServerException extends ApplicationException
{
    protected String reason;

    protected ServerException()
    {
        exceptionPrefix = "EXC-0";
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }
}

