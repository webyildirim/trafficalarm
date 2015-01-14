package com.structure;


import com.structure.util.ObjectUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;


public class GridData implements Serializable
{
    private Collection<BaseEntity> synchBuffer;
    private Vector<BaseEntity> dataCollection;
    private Collection<BaseEntity> newBuffer, updatedBuffer, garbageData;
    private Object dataObject;
    private Collection selectedRowCollection;

    private int gridType;

    public final static int DATATYPE_MULTIROW = 0;
    public final static int DATATYPE_SINGLEROW = 1;

    public GridData()
    {
        this.gridType = DATATYPE_MULTIROW;
        initializeBuffers();
    }

    public GridData(int gridType)
    {
        super();
        this.gridType = gridType;
        initializeBuffers();
    }

    private void initializeBuffers()
    {
        dataCollection = new Vector<BaseEntity>();
        newBuffer = new ArrayList<BaseEntity>();
        updatedBuffer = new ArrayList<BaseEntity>();
        synchBuffer = new ArrayList<BaseEntity>();
        garbageData = new ArrayList<BaseEntity>();
        selectedRowCollection = new ArrayList();
    }

    public void clear()
    {
        dataObject = null;
        dataCollection.clear();
        newBuffer.clear();
        synchBuffer.clear();
        updatedBuffer.clear();
        garbageData.clear();
    }

    public void initializeGridData(Collection<BaseEntity> dataObjects)
    {
        clear();
        dataCollection.addAll(dataObjects);
        synchBuffer = new ArrayList<BaseEntity>(dataObjects);
    }

    public void initializeGridData(BaseEntity baseEntity)
    {
        clear();
        dataObject = baseEntity;
        dataCollection.add(baseEntity);
        synchBuffer.add(baseEntity);
    }

    public void setDataCollection(Vector<BaseEntity> dataCollection)
    {
        this.dataCollection = dataCollection;
    }

    public void removeData(BaseEntity dataObject)
    {
        if (newBuffer.contains(dataObject))
            newBuffer.remove(dataObject);
        if (updatedBuffer.contains(dataObject))
        {
            updatedBuffer.remove(dataObject);
            garbageData.add(dataObject);
        } else
            garbageData.add(dataObject);
        dataCollection.remove(dataObject);
    }

    public void updateData(BaseEntity dataObject)
    {
        int index;
        if (newBuffer.contains(dataObject))
        {
            newBuffer.remove(dataObject);
            newBuffer.add(dataObject);
            index = dataCollection.indexOf(dataObject);
            dataCollection.remove(index);
            dataCollection.add(index, dataObject);
        } else if (updatedBuffer.contains(dataObject))
        {
            updatedBuffer.remove(dataObject);
            updatedBuffer.add(dataObject);
            index = dataCollection.indexOf(dataObject);
            dataCollection.remove(index);
            dataCollection.add(index, dataObject);
        } else if (synchBuffer.contains(dataObject))
        {
            //synchBuffer.remove(dataObject); //maybe required
            updatedBuffer.add(dataObject);
            index = dataCollection.indexOf(dataObject);
            dataCollection.remove(index);
            dataCollection.add(index, dataObject);
        }

        if (getGridType() == GridData.DATATYPE_SINGLEROW)
            this.dataObject = dataObject;
    }

    public void addUpdatedGridData(BaseEntity dataObject)
    {
        if (!newBuffer.contains(dataObject))
        {
            if (updatedBuffer.contains(dataObject))
                updatedBuffer.remove(dataObject);
            updatedBuffer.add(dataObject);
        }
    }

    public void addNewGridData(BaseEntity dataObject)
    {
        newBuffer.add(dataObject);
        dataCollection.add(dataObject);
    }

    public Vector<BaseEntity> getDataCollection()
    {
        return dataCollection;
    }

    public void setGarbageData(Collection<BaseEntity> garbageData)
    {
        this.garbageData = garbageData;
    }

    public Collection<BaseEntity> getGarbageData()
    {
        return garbageData;
    }

    public void setNewBuffer(Collection<BaseEntity> newBuffer)
    {
        this.newBuffer = newBuffer;
    }

    public Collection<BaseEntity> getNewBuffer()
    {
        return newBuffer;
    }

    public void setUpdatedBuffer(Collection<BaseEntity> updatedBuffer)
    {
        this.updatedBuffer = updatedBuffer;
    }

    public Collection<BaseEntity> getUpdatedBuffer()
    {
        return updatedBuffer;
    }

    public void setSynchBuffer(Collection<BaseEntity> synchBuffer)
    {
        this.synchBuffer = synchBuffer;
    }

    public Collection<BaseEntity> getSynchBuffer()
    {
        return synchBuffer;
    }

    public boolean isDataObjectUpdated()
    {
        if (updatedBuffer.contains(getDataObject()))
            return true;
        if (newBuffer.contains(getDataObject()))
            return true;

        return false;
    }

    public void setDataObject(Object dataObject)
    {
        this.dataObject = dataObject;
    }

    public Object getDataObject()
    {
        return dataObject;
    }

    public int getSize()
    {
        return dataCollection.size();
    }

    public void updateDataCollectionWithSynch(Collection<BaseEntity> synchBuffer)
    {
        synchBuffer.removeAll(getGarbageData());
        initializeGridData(synchBuffer);
    }

    public void updateObjectDataWithSynch(Collection<BaseEntity> synchBuffer)
    {
        synchBuffer.removeAll(getGarbageData());
        initializeGridData((BaseEntity)ObjectUtil.getObject(synchBuffer, 0));
    }

    public void setSelectedRowCollection(Collection selectedRowCollection)
    {
        this.selectedRowCollection = selectedRowCollection;
    }

    public Collection getSelectedRowCollection()
    {
        return selectedRowCollection;
    }

    public void setGridType(int gridType)
    {
        this.gridType = gridType;
    }

    public int getGridType()
    {
        return gridType;
    }
}
