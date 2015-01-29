
package com.structure.exception;


public class ReferentialConstraintException extends InfrastructureException
{
    public ReferentialConstraintException()
    {
        exceptionType = ApplicationException.EXCEPTION_TYPE_ERROR;
        exceptionCode = "05";
    }

    @Override
    public String getReason()
    {
        return "";
    }
}
