package com.accmee.structure.logger;


import com.accmee.structure.BaseEntity;

public class PreLoggedProcess
{
    private StackTraceElement[] elements;
    private BaseEntity entity;
    private Character process;
    private String userName;

    public PreLoggedProcess(StackTraceElement[] elements, BaseEntity entity, Character process, String userName)
    {
        this.elements = elements;
        this.entity = entity;
        this.process = process;
        this.userName = userName;
    }

    public StackTraceElement[] getElements()
    {
        return elements;
    }

    public void setElements(StackTraceElement[] elements)
    {
        this.elements = elements;
    }

    public BaseEntity getEntity()
    {
        return entity;
    }

    public void setEntity(BaseEntity entity)
    {
        this.entity = entity;
    }

    public Character getProcess()
    {
        return process;
    }

    public void setProcess(Character process)
    {
        this.process = process;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }


}
