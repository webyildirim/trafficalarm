package com.structure;


import com.structure.util.ObjectUtil;
import com.structure.util.StringUtility;

import java.io.Serializable;

public class Template implements Serializable
{
    public boolean isNullOrEmpty(String stringValue)
    {
        return StringUtility.isNullOrEmpty(stringValue);
    }

    public boolean isNull(Object object)
    {
        return ObjectUtil.isNull(object);
    }

    public boolean isNotNull(Object object)
    {
        return ObjectUtil.isNotNull(object);
    }
}
