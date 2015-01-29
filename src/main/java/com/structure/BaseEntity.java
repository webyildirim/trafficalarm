package com.structure;


import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.util.Assert;

import com.structure.util.LocaleUtil;
import com.structure.util.ObjectUtil;
import com.structure.util.StringUtility;


@MappedSuperclass
public abstract class BaseEntity implements Serializable
{
    //private transient Logger logger=Logger.getLogger(BaseEntity.class);
    protected int version;
    protected Date updateDate;
    protected String updateUserName = "";
    protected int status = STATUS_ACTIVE;
    protected String entityName="";

    public final static int STATUS_ACTIVE = 0;
    public final static int STATUS_CANCELED = 1;

    @Id
    private String id;

    public BaseEntity() {
        this(UUID.randomUUID());
    }

    public BaseEntity(UUID guid) {
        Assert.notNull(guid, "UUID is required");
        id = guid.toString();
        this.updateDate = new Date();
    }

    public BaseEntity(String guid) {
        Assert.notNull(guid, "UUID is required");
        id = guid;
        this.updateDate = new Date();
    }

    @Transient
    public String getEntityName()
    {
        return entityName;
    }
    public void setEntityName(String entityName)
    {
        this.entityName = entityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
		this.id = id;
	}

	public int getVersion() {
        return version;
    }
    
    public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

    public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

	@Transient
    public boolean isCancelled()
    {
        return STATUS_CANCELED == getStatus() ? true : false;
    }

    @Transient
    public boolean isPersisted()
    {
        return getUpdateDate()==null ? false : true;
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
        this.id=null;
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

