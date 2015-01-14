package com.structure.logger;

import java.util.Date;

public class LoggedProcess
{
    private String user;
    private char process;
    private String method;
    private int version;
    private String entityName;
    private long entityId;
    private String entityValue;
    private Date dateOfProcess;

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public char getProcess()
    {
        return process;
    }

    public void setProcess(char process)
    {
        this.process = process;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public int getVersion()
    {
        return version;
    }

    public void setVersion(int version)
    {
        this.version = version;
    }

    public String getEntityName()
    {
        return entityName;
    }

    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    public long getEntityId()
    {
        return entityId;
    }

    public void setEntityId(long entityId)
    {
        this.entityId = entityId;
    }

    public String getEntityValue()
    {
        return entityValue;
    }

    public void setEntityValue(String entityValue)
    {
        this.entityValue = entityValue;
    }

    public Date getDateOfProcess()
    {
        return dateOfProcess;
    }

    public void setDateOfProcess(Date dateOfProcess)
    {
        this.dateOfProcess = dateOfProcess;
    }

}
