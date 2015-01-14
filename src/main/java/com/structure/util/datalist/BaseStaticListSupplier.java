package com.structure.util.datalist;


import java.util.Collection;
import java.util.Vector;

public abstract class BaseStaticListSupplier implements StaticListSupplier
{
    protected Vector<StaticData> list;
    protected Collection itemList;
    protected boolean blankItemApplied;

    //always override your list loading method in case you are using a simple listsupplier

    abstract protected void loadList();

    public Object convertValueToObject(int value)
    {
        return list.get(value);
    }

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
        //since this is a static list refresh operation always return the same values
    }

    public abstract String getListName();

    public void setList(Collection list)
    {
        this.list.clear();
        this.list.addAll(list);
    }

    public void clearList()
    {
        this.list.clear();
        this.itemList.clear();
    }

    public boolean isVolatile()
    {
        return false;
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
