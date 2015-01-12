package com.accmee.structure.exception;

public abstract class InfrastructureException extends ServerException
{
    protected InfrastructureException()
    {
        exceptionSubPrefix = "0";
    }

}