package com.structure.exception;

import java.io.Serializable;

public abstract class ApplicationException extends Exception implements Serializable
{
    private static final long serialVersionUID = 1L;
    protected String exceptionPrefix = "ARS-0";
    protected String exceptionSubPrefix = "0";
    protected String exceptionCode = "00";
    protected int exceptionType = EXCEPTION_TYPE_INFO;

    public static final int EXCEPTION_TYPE_INFO = 1;
    public static final int EXCEPTION_TYPE_WARNING = 2;
    public static final int EXCEPTION_TYPE_ERROR = 3;

    public String getExceptionPrefix()
    {
        return exceptionPrefix;
    }

    public String getExceptionSubPrefix()
    {
        return exceptionSubPrefix;
    }

    public String getExceptionCode()
    {
        return exceptionCode;
    }

    public int getExceptionType()
    {
        return exceptionType;
    }

    public String getCompleteCode()
    {
        return getExceptionPrefix() + "" + getExceptionSubPrefix() + "" + getExceptionCode();
    }

    abstract public String getReason();
}
