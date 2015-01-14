package com.structure;


import com.structure.util.LocaleUtil;
import com.structure.util.ObjectUtil;
import com.structure.util.StringUtility;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.proxy.HibernateProxy;


public abstract class SuperEntity implements Serializable, Comparable
{
    protected Long id;
    protected String entityName = "";

    @Transient
    public String getEntityName()
    {
        return entityName;
    }

    @Transient
    abstract public Long getId();

    abstract public void setId(Long id);

    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    @Transient
    public boolean isPersisted()
    {
        return getId() == 0 ? false : true;
    }

    @Transient
    public boolean equals(Object object)
    {
        if (!(object instanceof SuperEntity))
            return false;

        SuperEntity tempEntity = (SuperEntity)object;
        if (ObjectUtil.isNull(tempEntity))
            return false;

        if (tempEntity.getEntityName().equals(getEntityName()) && tempEntity.getPrimaryKeyValue().equals(getPrimaryKeyValue()))
        {
            if (tempEntity.isPersisted())
                return true;
            else
            {
                try
                {
                    return this.toString().equals(tempEntity.toString());
                }
                catch (Exception exc)
                {
                    return super.equals(tempEntity);
                }
            }
        }

        return super.equals(tempEntity);
    }

    @Transient
    public int hashCode()
    {
        if (isPersisted())
            return (int) (getId().longValue() + (getEntityName().hashCode() * 100));
        return super.hashCode();
    }

    @Transient
    public SuperEntity copyEntity() throws CloneNotSupportedException
    {
        SuperEntity superEntity = clone();
        superEntity.clearIdFields();

        return superEntity;
    }

    @Transient
    public SuperEntity cloneRowObject() throws CloneNotSupportedException
    {
        return copyEntity();
    }

    @Transient
    public SuperEntity shallowCopy() throws CloneNotSupportedException
    {
        return (SuperEntity)super.clone();
    }

    @Transient
    public SuperEntity clone() throws CloneNotSupportedException
    {
        SuperEntity obj = (SuperEntity)super.clone();

        ObjectUtil.deepCopy(this, obj, this.getClass(), false);

        return obj;
    }

    @Transient
    public void initialize(Collection collection, int levelCursor, int level)
    {
        if (collection instanceof PersistentBag)
        {
            if (ObjectUtil.isNull(((PersistentBag)collection).getSession()))
                return;
            else
            {
                Iterator itr = ((Collection)collection).iterator();
                while (itr.hasNext())
                {
                    if (levelCursor < level)
                        ((SuperEntity)itr.next()).initialize(levelCursor, level);
                    else
                        itr.next();
                }
            }
        } else
        {
            Iterator itr = ((Collection)collection).iterator();
            while (itr.hasNext())
            {
                if (levelCursor < level)
                    ((SuperEntity)itr.next()).initialize(levelCursor, level);
                else
                    itr.next();
            }
        }
    }

    @Transient
    public void clearIdFields()
    {
        setId(new Long(0));
    }

    @Transient
    public void initialize(Collection collection, int level)
    {
        initialize(collection, 0, level);
    }


    /*
         * calls initialize method with default levelCursor 0
         * means just started initializing
         */

    @Transient
    public void initialize(int level)
    {
        initialize(0, level);
    }

    /**
     * Searches for column and join column annotations for getter methods.
     * If found then tries to initialize childs
     * @param levelCursor
     * @param level
     */
    @Transient
    public void initialize(int levelCursor, int level)
    {
        levelCursor++;

        Method[] methods = this.getClass().getMethods();

        Object obj = null;

        try
        {
            for (Method method : methods)
            {
                if (method.getAnnotation(JoinColumn.class) != null || method.getAnnotation(JoinTable.class) != null || method.getAnnotation(OneToMany.class) != null)
                {
                    Object result = method.invoke(this, new Object[0]);
                    if (result == null)
                        continue;

                    if (result instanceof SuperEntity)
                    {
                        if (levelCursor < level)
                            ((SuperEntity)result).initialize(levelCursor, level);
                    } else if (result instanceof Collection)
                        initialize((Collection)result, levelCursor, level);
                }
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            //logger.error(exc);
        }
    }

    public Object writeReplace() throws ObjectStreamException
    {
        Method[] methods = this.getClass().getMethods();

        try
        {
            for (Method method : methods)
            {
                if (method.getAnnotation(JoinColumn.class) != null || method.getAnnotation(JoinTable.class) != null)
                {
                    Object result = method.invoke(this, new Object[0]);
                    if (result == null)
                        continue;
                    if (result instanceof HibernateProxy)
                    {
                        if (((HibernateProxy)result).getHibernateLazyInitializer().isUninitialized())
                        {
                            //logger.info("Entity initialize edilmemis olabilir : "+method.getName());
                            Method x = this.getClass().getMethod(method.getName().replaceAll("get", "set"), method.getReturnType());
                            x.invoke(this, new Object[1]);
                        }
                    }
                }
            }
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
            //logger.error(exc);
        }

        return this;
    }


    @Transient
    abstract public String toString();

    @Override
    @Transient
    public int compareTo(Object object)
    {
        if (!(object instanceof SuperEntity))
            throw new ClassCastException();

        SuperEntity superEntity = (SuperEntity)object;
        Long idA = getId();
        Long idB = superEntity.getId();

        return idA.compareTo(idB);
    }

    public Object getPrimaryKeyValue()
    {
        Object result = null;
        try
        {
            result = getPrimaryKeyValueOfClass(this.getClass());
        }
        catch (Exception e)
        {
            return null;
        }

        return result;
    }

    private Object getPrimaryKeyValueOfClass(Class<?> type)
    {
        try
        {
            Method[] methods = type.getDeclaredMethods();
            for (Method method : methods)
            {
                //will be logged only if method is annotated with Logged
                if (method.getAnnotation(Id.class) == null)
                    continue;
                return method.invoke(this, new Object[0]);
            }
            if (type.getSuperclass() != null)
                return getPrimaryKeyValueOfClass(type.getSuperclass());
        }
        catch (Exception e)
        {
            return null;
        }

        return null;
    }

    public String getPrimaryKeyField()
    {
        String fieldName = null;
        try
        {
            fieldName = getPrimaryKeyFieldOfClass(this.getClass());
        }
        catch (Exception e)
        {
            return null;
        }

        return fieldName;
    }

    private String getPrimaryKeyFieldOfClass(Class<?> type)
    {
        String tempString = null;
        try
        {
            Method[] methods = type.getDeclaredMethods();
            for (Method method : methods)
            {
                //will be logged only if method is annotated with Logged
                if (method.getAnnotation(Id.class) == null)
                    continue;

                //in order to find the name of field by using methodName which is annotated with Id class
                tempString = method.getName();
                if (tempString.indexOf("get") < 0)
                    continue;

                tempString = tempString.substring(3, tempString.length());
                tempString = StringUtility.unCapitalize(tempString, 0, 1, LocaleUtil.EN.getLocale());
                return tempString;
            }
            if (type.getSuperclass() != null)
                return getPrimaryKeyFieldOfClass(type.getSuperclass());
        }
        catch (Exception e)
        {
            return null;
        }

        return null;
    }
}
