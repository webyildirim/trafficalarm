package com.structure.exception;


public class RequiredFieldEmptyException extends InfrastructureException
{
    private String field;

    public RequiredFieldEmptyException(String field)
    {
        exceptionCode = "07";
        exceptionType = ApplicationException.EXCEPTION_TYPE_ERROR;

        this.field = field;
    }

    @Override
    public String getReason()
    {
        return field;
    }
}
