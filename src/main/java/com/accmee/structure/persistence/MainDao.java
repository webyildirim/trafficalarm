package com.accmee.structure.persistence;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.springframework.security.core.context.SecurityContextHolder;

import com.accmee.structure.BaseEntity;
import com.accmee.structure.BaseFilter;
import com.accmee.structure.GridData;
import com.accmee.structure.ServerResponse;
import com.accmee.structure.SuperEntity;
import com.accmee.structure.exception.CascadeRemovedEntityException;
import com.accmee.structure.exception.ModifiedEntityException;
import com.accmee.structure.exception.RemovedEntityException;
import com.accmee.structure.logger.LogUtil;
import com.accmee.structure.util.ObjectUtil;

public class MainDao
{
    public void removeEntities(EntityManager manager, ServerResponse response, Collection entityList) throws Exception
    {
        Iterator itr = entityList.iterator();
        BaseEntity baseEntity = null;

        while (itr.hasNext())
        {
            baseEntity = (BaseEntity)itr.next();
            try
            {
                removeEntity(manager, baseEntity);
            }
            catch (ModifiedEntityException exc)
            {
                response.addData(exc.getLoadedEntity());
                response.addException(exc);
            }
            catch (RemovedEntityException exc)
            {
                response.addException(exc);
            }
            catch (Exception exc)
            {
                throw exc;
            }
        }
    }

    public void persistEntities(EntityManager manager, ServerResponse response, Collection entityList) throws Exception
    {
        Iterator itr = entityList.iterator();
        BaseEntity baseEntity = null;
        try
        {
            while (itr.hasNext())
            {
                baseEntity = (BaseEntity)itr.next();
                try
                {
                    baseEntity = (BaseEntity)(saveOrUpdateEntity(manager, baseEntity));
                    response.addData(baseEntity);
                }
                catch (ModifiedEntityException exc)
                {
                    response.addException(exc);
                    response.addData(exc.getLoadedEntity());
                }
                catch (RemovedEntityException exc)
                {
                    response.addException(exc);
                }
                catch (Exception exc)
                {
                    throw exc;
                }
            }
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        }
    }

    public void refreshEntities(EntityManager manager, ServerResponse response, Collection entityList)
    {
        Iterator itr = entityList.iterator();
        BaseEntity baseEntity = null;
        while (itr.hasNext())
        {
            baseEntity = (BaseEntity)itr.next();
            try
            {
                baseEntity = (BaseEntity)refreshEntity(manager, baseEntity);
                if (!response.getReturnData().contains(baseEntity))
                    response.addData(baseEntity);
            }
            catch (RemovedEntityException exc)
            {
                response.addException(exc);
            }
        }
    }

    public void initializeCollection(Collection collectionToInitialize)
    {
        Iterator itr = collectionToInitialize.iterator();
        while (itr.hasNext())
            itr.next();
    }

    public BaseEntity saveOrUpdateEntity(EntityManager manager, BaseEntity entity) throws Exception
    {
        return saveOrUpdateEntity(manager, entity, true);
    }

    public BaseEntity saveOrUpdateEntity(EntityManager manager, BaseEntity entity, boolean checkForVersion) throws Exception
    {
        if (ObjectUtil.isNull(entity))
            return null;
        //BaseEntity loadedEntity = null;
        
        String principal = SecurityContextHolder.getContext()!=null ? SecurityContextHolder.getContext().getAuthentication().getName():"";

        if (checkForVersion)
        {
            if (entity.isPersisted())
            {
                int version = getEntityVersionForPrimaryKey(manager, entity);
                //if any version record is found before then it's not removed
                if (version > -1)
                {
                    if (version != entity.getVersion())
                        throw new ModifiedEntityException(entity, findByPrimaryKey(manager, entity));
                } else
                    throw new RemovedEntityException(entity);

                //				loadedEntity=findByPrimaryKey(manager, entity);
                //				if(loadedEntity!=null)
                //				{
                //					if(loadedEntity.getVersion()!=entity.getVersion())
                //						throw new ModifiedEntityException(entity,loadedEntity);
                //				}
                //				else
                //					throw new RemovedEntityException(entity);				
            }
        }
        entity.setVersion(entity.getVersion() + 1);
        BaseEntity persistedEntity = manager.merge(entity);

        try
        {
            //manager.flush just coded in order to weblogic(bea) could handle the persistenceexception, otherwise weblogic swallow the hibernate ejb exception,
            //essentially this should not used such that because of not recommended, actually must be automatically flush whenever transaction ended
            manager.flush();
        }
        catch (Exception exc)
        {
            throw exc;
        }

        LogUtil.getInstance().logProcess(persistedEntity, LogUtil.PERSIST, principal);

        return persistedEntity;
    }

    public void findAndRemoveEntity(EntityManager manager, BaseEntity entity) throws Exception
    {
        entity = (BaseEntity)findByPrimaryKey(manager, entity);
        if (entity != null)
            removeEntity(manager, entity, true);
    }

    public void removeEntities(EntityManager manager, Collection<? extends BaseEntity> entities)
    {
        removeEntities(manager, entities, false);
    }

    public void removeEntities(EntityManager manager, Collection<? extends BaseEntity> entities, boolean checkForVersion)
    {
        try
        {
            for (BaseEntity entity : entities)
                removeEntity(manager, entity, checkForVersion);
        }
        catch (Exception exc)
        {
            System.out.println(exc.toString());
        }
    }

    public void removeEntity(EntityManager manager, BaseEntity entity) throws Exception
    {
        removeEntity(manager, entity, true);
    }

    public void removeEntity(EntityManager manager, BaseEntity entity, boolean checkForVersion) throws Exception
    {
        if (ObjectUtil.isNull(entity))
            return;

        if (!entity.isPersisted())
            return;        
        
        String principal = SecurityContextHolder.getContext()!=null ? SecurityContextHolder.getContext().getAuthentication().getName():"";


        if (!checkForVersion)
        {
            manager.remove(entity);
            LogUtil.getInstance().logProcess(entity, LogUtil.REMOVE, principal);
        } else
        {
            BaseEntity loadedEntity = (BaseEntity)findByPrimaryKey(manager, entity);
            if (loadedEntity != null)
            {
                if (loadedEntity.getVersion() == entity.getVersion())
                {
                    manager.remove(loadedEntity);
                    LogUtil.getInstance().logProcess(loadedEntity, LogUtil.REMOVE, principal);
                } else
                    throw new ModifiedEntityException(entity, loadedEntity);
            } else
                throw new RemovedEntityException(entity);
        }

        try
        {
            //manager.flush just coded in order to weblogic(bea) could handle the persistenceexception, otherwise weblogic swallow the hibernate ejb exception,
            //essentially this should not used such that because of not recommended, actually must be automatically flush whenever transaction ended
            manager.flush();
        }
        catch (Exception exc)
        {
            throw exc;
        }
    }

    public BaseEntity refreshEntity(EntityManager manager, BaseEntity entity) throws RemovedEntityException
    {
        BaseEntity loadedEntity = (BaseEntity)findByPrimaryKey(manager, entity);
        if (loadedEntity == null)
            throw new RemovedEntityException(entity);
        return loadedEntity;
    }

    //	public BaseEntity refreshSelfReferencedEntity(EntityManager manager, BaseEntity entity) throws CascadeRemovedEntityException, RemovedEntityException
    //	{
    //		BaseEntity refreshedEntity=manager.find(entity.getClass(), entity.getId());
    //		if(refreshedEntity==null)
    //			throw new CascadeRemovedEntityException(entity);
    //		BaseEntity loadedEntity=refreshEntity(manager,entity);		
    //		
    //		return loadedEntity;
    //	}

    private int getEntityVersionForPrimaryKey(EntityManager manager, BaseEntity entity)
    {
        StringBuilder queryString = new StringBuilder();

        queryString.append("\nSELECT version FROM ").append(entity.getEntityName()).append(" e WHERE e.id=:id\n");
        Query query = manager.createQuery(queryString.toString());
        query.setParameter("id", entity.getId());

        int result = -1;

        try
        {
            result = (Integer)query.getSingleResult();
        }
        catch (NoResultException exc)
        {
            return -1;
        }

        return result;
    }

    public BaseEntity findByPrimaryKey(EntityManager manager, BaseEntity entity)
    {
        BaseEntity baseEntity = null;
        String qryString = "\nFROM " + entity.getEntityName() + " e WHERE e.id=:id\n";
        Query query = manager.createQuery(qryString);
        query.setParameter("id", entity.getId());

        try
        {
            Object found = query.getSingleResult();
            baseEntity = (BaseEntity)found;
        }
        catch (NoResultException exc)
        {
            return null;
        }

        return baseEntity;
    }

    //will be rename findByPrimaryKey and old one must be rename as findById
    public SuperEntity findByPrimaryKey(EntityManager manager, SuperEntity entity)
    {
        SuperEntity superEntity = null;
        
        String keyField = entity.getPrimaryKeyField();
        StringBuilder queryString = new StringBuilder("\n FROM ");
        queryString.append(entity.getEntityName()).append(" e WHERE e.").append(keyField).append("=:").append(keyField).append("\n");
        
        Query query = manager.createQuery(queryString.toString());
        query.setParameter(keyField, entity.getPrimaryKeyValue());

        try
        {
            Object found = query.getSingleResult();
            superEntity = (SuperEntity)found;
        }
        catch (NoResultException exc)
        {
            return null;
        }

        return superEntity;
    }
    
    /**
     * finds all records mapped to this entity
     * @param manager
     * @param entity
     * @return
     */
    public Collection getEntityList(EntityManager manager, BaseEntity entity)
    {
        Query query = manager.createQuery("SELECT entity " + "FROM " + entity.getEntityName() + " entity");
        return query.getResultList();
    }

    public Collection getEntityList(EntityManager manager, SuperEntity entity)
    {
        Query query = manager.createQuery("SELECT entity " + "FROM " + entity.getEntityName() + " entity");
        return query.getResultList();
    }

    //finds and returns all objects that matches

    public Collection findByFilter(EntityManager manager, BaseFilter baseFilter)
    {
        Query query = baseFilter.createQuery(manager);

        return query.getResultList();
    }


    //finds matching object that is filtered
    //if more than one match found then the first one is returned

    public Object findObjectByFilter(EntityManager manager, BaseFilter baseFilter)
    {
        Query query = baseFilter.createQuery(manager);

        Object result = null;

        try
        {
            result = query.getSingleResult();
        }
        catch (NonUniqueResultException exc)
        {
            return ObjectUtil.getObject(findByFilter(manager, baseFilter), 0);
        }
        catch (NoResultException exc)
        {
            return null;
        }

        return result;
    }

    public Object findObjectByFilter(EntityManager manager, BaseFilter baseFilter, String queryMethod) throws NoSuchMethodException, InvocationTargetException,
                                                                                                              IllegalAccessException
    {
        Object result = null;
        try
        {
            result = createQueryForFilter(manager, baseFilter, queryMethod).getSingleResult();
        }
        catch (NonUniqueResultException exc)
        {
            return ObjectUtil.getObject(findByFilter(manager, baseFilter, queryMethod), 0);
        }
        catch (NoResultException exc)
        {
            return null;
        }

        return result;
    }

    public Collection findByFilter(EntityManager manager, BaseFilter baseFilter, String queryMethod) throws NoSuchMethodException, InvocationTargetException,
                                                                                                            IllegalAccessException
    {
        return createQueryForFilter(manager, baseFilter, queryMethod).getResultList();
    }

    public Query createQueryForFilter(EntityManager manager, BaseFilter baseFilter, String queryMethod) throws NoSuchMethodException, InvocationTargetException,
                                                                                                               IllegalAccessException
    {
        //System.out.println(baseFilter.getClass().toString());
        Class[] parameters = { EntityManager.class };
        Class filterClass = baseFilter.getClass();

        Method methodToRun = filterClass.getMethod(queryMethod, parameters);
        Object returnedObject = methodToRun.invoke(baseFilter, new Object[] { manager });

        Query query = (Query)returnedObject;

        return query;
    }

    public ServerResponse synchronizeEntities(EntityManager manager, GridData gridData) throws Exception
    {
        return synchronizeEntities(manager, gridData, false);
    }

    public ServerResponse synchronizeEntities(EntityManager manager, GridData gridData, ServerResponse response) throws Exception
    {
        return synchronizeEntities(manager, gridData, response, false);
    }

    public ServerResponse synchronizeEntities(EntityManager manager, GridData gridData, boolean selfReferenced) throws Exception
    {
        return synchronizeEntities(manager, gridData, new ServerResponse(), selfReferenced);
    }

    public ServerResponse synchronizeEntities(EntityManager manager, GridData gridData, ServerResponse response, boolean selfReferenced) throws Exception
    {
        try
        {
            //removeEntities(manager, response, gridData.getGarbageData());
            Collection entities = new ArrayList();
            entities.addAll(gridData.getNewBuffer());
            entities.addAll(gridData.getUpdatedBuffer());
            persistEntities(manager, response, entities);
            removeEntities(manager, response, gridData.getGarbageData());
            gridData.getSynchBuffer().removeAll(gridData.getGarbageData());

            if (!selfReferenced)
                refreshEntities(manager, response, gridData.getSynchBuffer());
            else
                refreshSelfReferencedEntity(manager, response, gridData.getSynchBuffer());
        }
        catch (Exception exc)
        {
            throw exc;
        }

        return response;
    }

    public void refreshSelfReferencedEntity(EntityManager manager, ServerResponse response, Collection entityList)
    {
        Iterator itr = entityList.iterator();
        BaseEntity baseEntity = null;
        while (itr.hasNext())
        {
            baseEntity = (BaseEntity)itr.next();
            try
            {
                baseEntity = refreshSelfReferencedEntity(manager, baseEntity);
                if (!response.getReturnData().contains(baseEntity))
                    response.addData(baseEntity);
            }
            catch (CascadeRemovedEntityException exc)
            {
                response.addException(exc);
            }
            catch (RemovedEntityException exc)
            {
                response.addException(exc);
            }
        }
    }

    public BaseEntity refreshSelfReferencedEntity(EntityManager manager, BaseEntity entity) throws CascadeRemovedEntityException, RemovedEntityException
    {
        BaseEntity refreshedEntity = (BaseEntity)manager.find(entity.getClass(), entity.getId());
        if (refreshedEntity == null)
            throw new CascadeRemovedEntityException(entity);
        BaseEntity loadedEntity = refreshEntity(manager, entity);

        return loadedEntity;
    }
}
