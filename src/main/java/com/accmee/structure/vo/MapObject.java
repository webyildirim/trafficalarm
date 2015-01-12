package com.accmee.structure.vo;

import javax.xml.bind.annotation.XmlRootElement;

import com.accmee.structure.Template;

@XmlRootElement
public class MapObject extends Template
{
    private String name;
    private Object value;

    public MapObject()
    {
    }

    public MapObject(String name, Object value)
    {
        this.name = name;
        this.value = value;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public Object getValue()
    {
        return value;
    }
}
