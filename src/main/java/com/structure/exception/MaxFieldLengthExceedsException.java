package com.structure.exception;


public class MaxFieldLengthExceedsException extends InfrastructureException
{
    private Exception reasonException;
    private String reason = "";

    public MaxFieldLengthExceedsException()
    {
        exceptionType = ApplicationException.EXCEPTION_TYPE_ERROR;
        exceptionCode = "17";
    }

    public MaxFieldLengthExceedsException(Exception exception)
    {
        this(exception, "");
    }

    public MaxFieldLengthExceedsException(String reason)
    {
        this(null, reason);
    }

    public MaxFieldLengthExceedsException(Exception exception, String reason)
    {
        exceptionType = ApplicationException.EXCEPTION_TYPE_ERROR;
        exceptionCode = "17";

        this.reasonException = exception;
        this.reason = reason;
    }

    @Override
    public String getReason()
    {
        return reason + (reasonException != null ? reasonException.toString() : "");
    }
}
