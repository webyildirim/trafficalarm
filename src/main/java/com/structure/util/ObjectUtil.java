package com.structure.util;


import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import com.structure.ListValue;
import com.structure.SuperEntity;


public class ObjectUtil
{

    public static void deepCopy(Object src, Object dest, Class objClass) throws CloneNotSupportedException
    {
        deepCopy(src, dest, objClass, false);
    }

    public static void deepCopy(Object src, Object dest, Class objClass, boolean canClone) throws CloneNotSupportedException
    {
        boolean ok = false;

        if (!ok)
            internalCopy(src, dest, objClass, true);
    }

    protected static void internalCopy(Object src, Object dest, Class objClass, boolean deepCopy) throws CloneNotSupportedException
    {

        cloneFields(src, dest, objClass, deepCopy);

    }

    private static void cloneFields(Object src, Object dest, Class objClass, boolean deep) throws CloneNotSupportedException
    {
        Field[] fields = null;
        try
        {
            fields = objClass.getDeclaredFields();
        }
        catch (Exception e)
        {
            System.out.println("Clone Exception at " + objClass.getName() + " - " + e.getMessage());
        }

        if (fields == null)
            return;

        Field field;

        for (int i = 0; i < fields.length; i++)
        {
            field = fields[i];
            int fieldMods = field.getModifiers();

            if (Modifier.isFinal(fieldMods))
                continue;

            field.setAccessible(true);

            try
            {
                Object val = field.get(src);

                if (deep && (!isPrimitive(field)))
                {
                    if (field.getType().isArray())
                    {
                        val = createCopy(val);
                    } else if (field.getType().equals(java.util.Collection.class))
                    {
                        if (val != null)
                        {
                            ArrayList destinationCollection = new ArrayList();
                            Collection sourceCollection = (Collection)val;
                            try
                            {
                                Iterator itr = sourceCollection.iterator();
                                while (itr.hasNext())
                                    destinationCollection.add(cloneObject(itr.next()));
                            }
                            catch (Exception exc)
                            {
                                System.out.println(exc.toString());
                            }
                            catch (Throwable thr)
                            {
                                System.out.println(thr.toString());
                            }

                            val = destinationCollection;
                        }
                    } else
                    {
                        val = cloneField(field, val);
                    }
                }

                field.set(dest, val);
            }
            catch (IllegalAccessException e)
            {

                System.out.println("Can not access " + field.getName());

                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public static boolean isPrimitive(Field f)
    {
        return f.getType().isPrimitive();
    }

    public static boolean isPrimitive(Class c)
    {
        return c.isPrimitive();
    }

    public static Object createCopy(Object src)
    {
        return createCopy(src, true);
    }

    public static Object createCopy(Object src, boolean deepCopy)
    {
        Object dest = null;
        if (src == null)
            return null;

        Class objClass = src.getClass();
        if (isPrimitive(objClass))
            return src;

        try
        {
            if (objClass.isArray())
            {
                int length = Array.getLength(src);
                dest = Array.newInstance(objClass.getComponentType(), length);

                Object o = null;
                for (int i = 0; i < length; i++)
                {
                    o = Array.get(src, i);
                    o = createCopy(o);
                    Array.set(dest, i, o);
                }
            } else if (objClass.equals(BigDecimal.class))
            {
                dest = (BigDecimal)src;
            } else
            {
                dest = objClass.newInstance();
                internalCopy(src, dest, objClass, deepCopy);
            }
        }
        catch (CloneNotSupportedException e)
        {
            System.out.println("ObjectUtil.createCopy()");
            System.out.println(e);
        }
        catch (IllegalAccessException e)
        {
            System.out.println("ObjectUtil.createCopy()");
            System.out.println(e);
        }
        catch (InstantiationException e)
        {
            System.out.println("ObjectUtil.createCopy()");
            System.out.println(e);
        }

        return dest;
    }

    public static Object cloneField(Field field, Object fieldVal) throws CloneNotSupportedException
    {
        if (fieldVal == null)
            return null;

        try
        {
            if (true)
                return cloneObject(fieldVal);
            /*
			 * Object has a public clone method. Try to use it.
			 */
            Method m = field.getType().getMethod("clone", new Class[0]);
            try
            {
                Object newValue = m.invoke(fieldVal, new Object[0]);
                return newValue;
            }
            catch (InvocationTargetException e)
            {
                Throwable t = e.getTargetException();

                if (t instanceof Error)
                {
                    throw (Error)t;
                } else if (t instanceof RuntimeException)
                {
                    throw (RuntimeException)t;
                } else if (t instanceof CloneNotSupportedException)
                {
                    return createCopy(fieldVal);
                } else
                {
                    throw new RuntimeException(t.getMessage());
                }
            }
        }
        catch (NoSuchMethodException e)
        {
            /*
			 * Can't find a clone() method, so forget trying to clone.
			 */
        }
        catch (IllegalAccessException e)
        {
            /*
			 * clone() method not accessible so forget trying to clone.
			 */
        }

        return fieldVal;
    }

    public static Object cloneObject(Object value) throws CloneNotSupportedException
    {
        try
        {
            Method m = value.getClass().getMethod("clone", new Class[0]);
            Object newValue = m.invoke(value, new Object[0]);
            return newValue;
        }
        catch (InvocationTargetException e)
        {
            Throwable t = e.getTargetException();

            if (t instanceof Error)
            {
                throw (Error)t;
            } else if (t instanceof RuntimeException)
            {
                throw (RuntimeException)t;
            } else if (t instanceof CloneNotSupportedException)
            {
                return createCopy(value);
            } else
            {
                throw new RuntimeException(t.getMessage());
            }
        }
        catch (NoSuchMethodException e)
        {
            return value;
        }
        catch (IllegalAccessException e)
        {
            return value;
        }
    }

    public static boolean isNull(Object object)
    {
        return (object == null) ? true : false;
    }

    public static boolean isNullOrEmpty(Collection list)
    {
        return (list == null) ? true : (list.isEmpty() ? true : false);
    }

    public static boolean isNullOrEmpty(Object[] objectArray)
    {
        return (objectArray == null) ? true : (objectArray.length==0 ? true : false);
    }

    public static boolean isNotNull(Object object)
    {
        return (!isNull(object));
    }

    public static Object getObject(Collection objects, int index) throws IndexOutOfBoundsException
    {
        int counter = 0;
        Object temp = null;
        Iterator itr = objects.iterator();
        while (itr.hasNext())
        {
            temp = itr.next();
            if (counter == index)
                return temp;

            counter++;
        }

        throw new ArrayIndexOutOfBoundsException();
    }


    public static Object getObject(Object[] objectArray, int index) throws IndexOutOfBoundsException
    {
        return objectArray[index];
    }

    public static Object findObject(Collection objects, Object object) throws IndexOutOfBoundsException
    {
        Object temp = null;
        Iterator itr = objects.iterator();
        while (itr.hasNext())
        {
            temp = itr.next();
            if (temp.equals(object))
                return temp;
        }

        throw new ArrayIndexOutOfBoundsException();
    }

    public static <T> ArrayList<T> collectionToArrayList(Collection<T> collection)
    {
        return new ArrayList<T>(collection);
    }

    public static Collection convertToCollection(Object... objects)
    {
        Collection list = new ArrayList();
        for (Object obj : objects)
            list.add(obj);

        // Wrong
        //		ArrayList list=new ArrayList();
        //		list.add(objects);

        return list;
    }

    public static <T> T coalesce(T objectA, T objectB)
    {
        return objectA != null ? objectA : (objectB != null ? objectB : objectA);
    }

    public static Object getDefaultObjectOfList(Collection list)
    {
        ListValue listValue = null;
        if (list != null && list.size() > 0)
        {
            Iterator itr = list.iterator();
            while (itr.hasNext())
            {
                listValue = (ListValue)itr.next();
                if (listValue.isDefaultValue())
                    break;
            }
        }

        return listValue;
    }

    public static Object findObjectWithinSuperEntityCollectionById(Collection list, int id)
    {
        SuperEntity superEntity = null;
        Iterator itr = list.iterator();
        while (itr.hasNext())
        {
            superEntity = (SuperEntity)itr.next();
            if (superEntity.getId() == id)
                break;
        }

        return superEntity;
    }

    public static Object findObjectWithinSuperEntityCollectionByPrimaryKey(Collection list, Object primaryKeyObject)
    {
        SuperEntity superEntity = null;
        Iterator itr = list.iterator();
        while (itr.hasNext())
        {
            superEntity = (SuperEntity)itr.next();
            if (superEntity.getPrimaryKeyValue().equals(primaryKeyObject))
                break;
        }

        return superEntity;
    }

    public static Object getFirstElementOfHashMap(LinkedHashMap map)
    {
        return map.entrySet().iterator().next();
    }
}
