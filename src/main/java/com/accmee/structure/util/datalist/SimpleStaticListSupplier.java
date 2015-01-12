package com.accmee.structure.util.datalist;

import java.util.ArrayList;
import java.util.Vector;

public abstract class SimpleStaticListSupplier extends BaseStaticListSupplier
{
    public SimpleStaticListSupplier()
    {
        setBlankItemApplied(true);
        list = new Vector<StaticData>();
        itemList = new ArrayList();
        //if (addList())
        //static suppliers must be reload everytime because of possible different languages case, should save lists with languages to achieve this issue
        loadList();
    }

    public SimpleStaticListSupplier(boolean blankItemApplied)
    {
        setBlankItemApplied(blankItemApplied);
        list = new Vector<StaticData>();
        itemList = new ArrayList();
        //if (addList())
        //static suppliers must be reload everytime because of possible different languages case, should save lists with languages to achieve this issue
        loadList();
    }

    public Vector getList(Object criteria)
    {
        return list;
    }

    @Override
    protected void loadList()
    {
    }
}
