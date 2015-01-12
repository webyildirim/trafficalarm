package com.accmee.structure.util.datalist;

import java.util.Collection;
import java.util.Vector;

public interface ListSupplier
{
    public String getListName();

    public Vector getList(Object criteria);

    public int getContentSize();

    public void setList(Collection list);

    public void clearList();

    public void refreshList();
}
