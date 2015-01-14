package com.structure.util.datalist;

import java.util.Collection;
import java.util.Vector;

public abstract class BaseListSupplier implements ListSupplier
{
    protected Vector list;
    protected Collection itemList;
    protected boolean blankItemApplied;
    //protected boolean radioButtonApplied;

    //always override your list loading method in case you are using a simple listsupplier

    abstract protected void loadList();

    //always override your criteria list loading method in case you are using a criteria listsupplier

    abstract protected void loadList(Object criteria);

    public boolean addList()
    {
        return ListManager.getInstance().addList(getListName(), this);
    }

    public int getContentSize()
    {
        return list.size();
    }

    public void refreshList()
    {
        clearList();
        loadList();
    }

    public abstract String getListName();

    public void setList(Collection list)
    {
        clearList();
        this.list.addAll(list);
    }

    public void clearList()
    {
        this.list.clear();
        this.itemList.clear();
    }

    public void setItemList(Collection itemList)
    {
        this.itemList = itemList;
    }

    public Collection getItemList()
    {
        return itemList;
    }

    public void setBlankItemApplied(boolean blankItemApplied)
    {
        this.blankItemApplied = blankItemApplied;
    }

    public boolean isBlankItemApplied()
    {
        return blankItemApplied;
    }
}
