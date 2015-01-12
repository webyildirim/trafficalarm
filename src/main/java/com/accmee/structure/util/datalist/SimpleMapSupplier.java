package com.accmee.structure.util.datalist;

import java.util.HashMap;

public abstract class SimpleMapSupplier extends SimpleListSupplier
{
    public HashMap map;

    public HashMap getMap()
    {
        return map;
    }

    @Override
    public void loadList()
    {
        map = new HashMap();
        loadMap();
    }

    protected abstract void loadMap();

}
