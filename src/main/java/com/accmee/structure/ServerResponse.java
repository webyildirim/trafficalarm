package com.accmee.structure;


import com.accmee.structure.exception.ApplicationException;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;


public class ServerResponse implements Serializable
{
    private ArrayList warningList;
    private Collection<ApplicationException> exceptionList;
    private Collection returnData;

    private boolean dirty = false;

    public ServerResponse()
    {
        warningList = new ArrayList();
        exceptionList = new ArrayList<ApplicationException>();
        returnData = new ArrayList();
    }

    public void addWarning(String warning)
    {
        warningList.add(warning);
    }

    public ArrayList getWarnings()
    {
        return warningList;
    }

    public void addException(ApplicationException exc)
    {
        exceptionList.add(exc);
    }

    public void addExceptionList(Collection exc)
    {
        exceptionList.addAll(exc);
    }

    public Collection getExceptionList()
    {
        return exceptionList;
    }

    public boolean isSuccessful()
    {
        return ((hasWarning() | hasError())) ? false : true;
    }

    public boolean hasWarning()
    {
        return (!warningList.isEmpty());
    }

    public boolean hasError()
    {
        return (!exceptionList.isEmpty());
    }

    public void addData(Object data)
    {
        returnData.add(data);
    }

    public void addDataList(Collection dataList)
    {
        returnData.addAll(dataList);
    }

    public Collection getReturnData()
    {
        return returnData;
    }

    public boolean isDirty()
    {
        return dirty;
    }

    public void setDirty(boolean dirty)
    {
        this.dirty = dirty;
    }
}
