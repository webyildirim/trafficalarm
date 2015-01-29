package com.structure.util.datalist;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import com.structure.util.ObjectUtil;


public class ListManager
{
    private HashMap<String, ListSupplier> dataLists;
    private static ListManager listManager = null;

    static
    {
        listManager = new ListManager();
    }

    private ListManager()
    {
        dataLists = new HashMap<String, ListSupplier>();
    }

    public static ListManager getInstance()
    {
        return listManager;
    }

    public Collection getLists()
    {
        return dataLists.values();
    }

    public boolean containsList(String listName)
    {
        return dataLists.containsKey(listName);
    }

    public void clearList(String listName)
    {
        getSupplier(listName).clearList();
    }

    public Vector getList(String listName)
    {
        return getList(listName, null);
    }

    public void refreshList(String listName)
    {
        if (ObjectUtil.isNotNull(getSupplier(listName)))
            getSupplier(listName).refreshList();
    }

    public void refreshList(String listName, Object criteria)
    {
        if (ObjectUtil.isNotNull(getSupplier(listName)))
            ((CriteriaListSupplier)getSupplier(listName)).refreshList(criteria);
    }

    public ListSupplier getSupplier(String listName)
    {
        return (ListSupplier)dataLists.get(listName);
    }

    public Vector getList(String listName, Object criteria)
    {
        ListSupplier supplier = (ListSupplier)dataLists.get(listName);
        return supplier.getList(criteria);
    }

    public Object convertValueToObject(String listName, int value)
    {
        StaticListSupplier supplier = (StaticListSupplier)dataLists.get(listName);
        return supplier.convertValueToObject(value);
    }

    public boolean addList(String listName, ListSupplier supplier)
    {
        if (containsList(listName))
        {
            clearList(listName);
            removeList(listName);
        }

        dataLists.put(listName, supplier);

        return true;
    }

    public void removeList(String listName)
    {
        dataLists.remove(listName);
    }

    public Collection getSortedLists()
    {
        Map sortedMap = new TreeMap(dataLists);

        return sortedMap.values();
    }

    //    public void createListWindow(Component component, String listName, Object criteria)
    //    {
    //        new ListWindow(component, getList(listName, criteria));
    //    }
    //
    //    public void createListWindow(Component component, String listName, Object criteria)
    //    {
    //        new ListWindow(linkedField, new Vector(list));
    //    }
    //    public void setDefault(Component component, String listName, Object criteria)
    //    {
    //        Vector list = getList(listName, criteria);
    //
    //        if (list.size() > 0)
    //            linkedField.linkedValueSet(list.get(0));
    //    }

}

