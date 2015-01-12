
package com.accmee.structure.exception;

import com.accmee.structure.exception.ApplicationException;
import com.accmee.structure.exception.InfrastructureException;

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
