package com.structure;


import com.structure.util.ObjectUtil;

import java.io.Serializable;

import javax.persistence.Transient;


public abstract class BaseCompositePK implements Serializable
{
    @Transient
    public boolean equals(Object object)
    {
        if (!(object instanceof BaseCompositePK))
            return false;

        BaseCompositePK basePK = (BaseCompositePK)object;
        if (ObjectUtil.isNull(basePK))
            return false;

        return super.equals(basePK);
    }
}
