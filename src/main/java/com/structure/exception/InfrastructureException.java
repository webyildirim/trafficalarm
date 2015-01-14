package com.structure.exception;

public abstract class InfrastructureException extends ServerException
{
    protected InfrastructureException()
    {
        exceptionSubPrefix = "0";
    }

}