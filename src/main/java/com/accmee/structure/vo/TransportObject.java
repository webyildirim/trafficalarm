package com.accmee.structure.vo;


import java.io.Serializable;

import java.util.HashMap;
import java.util.Set;


public class TransportObject implements Serializable
{
    private HashMap<Object, Object> map;

    public TransportObject()
    {
        map = new HashMap<Object, Object>();
    }

    public void put(Object key, Object value)
    {
        map.put(key, value);
    }

    public Object get(Object key)
    {
        return map.get(key);
    }

    public Set<?> getKeySet()
    {
        return map.keySet();
    }
}
