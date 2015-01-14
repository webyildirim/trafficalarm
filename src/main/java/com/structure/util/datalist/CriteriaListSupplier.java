package com.structure.util.datalist;

import java.util.ArrayList;
import java.util.Vector;

public abstract class CriteriaListSupplier extends BaseListSupplier
{
    protected Object criteria;
    protected boolean refreshNeeded;

    public CriteriaListSupplier(Object criteria)
    {
        this.criteria = criteria;
        setBlankItemApplied(true);
        list = new Vector();
        itemList = new ArrayList();
        if (addList())
            loadList(criteria);
    }

    public CriteriaListSupplier(boolean blankItemApplied, Object criteria)
    {
        this.criteria = criteria;
        setBlankItemApplied(blankItemApplied);
        list = new Vector();
        itemList = new ArrayList();
        if (addList())
            loadList(criteria);
    }

    public Vector getList(Object criteria)
    {
        if (isRefreshNeeded(criteria))
            refreshList();

        return list;
    }

    public void refreshList(Object criteria)
    {
        this.criteria = criteria;
        clearList();
        if (!(criteria == null && !isEmptyCriteriaAccepted()))
            loadList(criteria);
    }

    // if criteria changes returns true

    public boolean isRefreshNeeded(Object criteria)
    {
        boolean refreshNeeded = false;

        if (getContentSize() == 0)
            refreshNeeded = true;
        else if (criteria != null ^ this.criteria != null)
            refreshNeeded = true;
        else
        {
            if (criteria != null && (!(criteria.equals(this.criteria))))
                refreshNeeded = true;
        }

        this.criteria = criteria;

        return refreshNeeded;
    }

    @Override
    protected void loadList()
    {
    }

    @Override
    protected void loadList(Object criteria)
    {
    }

    protected boolean isEmptyCriteriaAccepted()
    {
        return true;
    }
}
