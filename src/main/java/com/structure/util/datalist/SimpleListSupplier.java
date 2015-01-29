package com.structure.util.datalist;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.structure.util.ObjectUtil;

public abstract class SimpleListSupplier extends BaseListSupplier
{
    public SimpleListSupplier()
    {
        setBlankItemApplied(true);
        //setRadioButtonApplied(false);
        list = new Vector();
        itemList = new ArrayList();
        if (addList())
            loadList();
    }

    public SimpleListSupplier(boolean blankItemApplied)
    {
        setBlankItemApplied(blankItemApplied);
        list = new Vector();
        itemList = new ArrayList();
        if (addList())
            loadList();
    }

    public boolean isVolatile()
    {
        return false;
    }

    public Vector getList(Object criteria)
    {
        return list;
    }

    public Collection getItemList()
    {
        return itemList;
    }

    @Override
    protected void loadList()
    {
    }

    @Override
    protected void loadList(Object criteria)
    {
    }

    public Object getDefaultValue()
    {
        return ObjectUtil.getDefaultObjectOfList(getList(null));
    }
}
