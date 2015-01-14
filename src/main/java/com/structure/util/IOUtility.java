package com.structure.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;


public class IOUtility
{
    private final static String defaultPropertiesFilePath = "./configuration.properties";

    public static Properties loadProperties(String fileName, Class resourceStreamClass)
    {
        Properties props = new Properties();
        InputStream in = null;
        try
        {
            in = resourceStreamClass.getResourceAsStream(fileName);
            props.load(in);
        }
        catch (Exception exc)
        {
            System.out.println("Failed to read " + fileName + " file!!!");
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
        return props;
    }

    public static Map<String, String> loadDefaultConfPropertiesFileAsMap()
    {
        return getPropertiesFileAsMap(defaultPropertiesFilePath);
    }


    public static Map<String, String> getPropertiesFileAsMap(String path)
    {
        Map<String, String> propertyMap = null;
        Properties prop = new Properties();
        InputStream is = null;
        try
        {
            propertyMap = new HashMap<String, String>();
            prop = new Properties();
            is = new FileInputStream(new File(path));
            prop.load(is);
            Iterator iterator = prop.entrySet().iterator();
            String name = "";
            String value = "";
            while (iterator.hasNext())
            {
                Map.Entry en = (Map.Entry)iterator.next();
                name = String.valueOf(en.getKey());
                value = String.valueOf(en.getValue());
                //System.out.println(en.getKey() + "    :   " + en.getValue());
                propertyMap.put(name, value);
            }
        }
        catch (Exception e)
        {
            System.err.println("Failed to read file: " + path);
            e.printStackTrace();
        }
        return propertyMap;
    }

    public static Map<String, String> getPropertiesFileAsMap(Properties properties)
    {
        Map<String, String> propertyMap = null;

        Iterator iterator = properties.entrySet().iterator();
        String name = "";
        String value = "";
        propertyMap = new HashMap<String, String>();
        while (iterator.hasNext())
        {
            Map.Entry en = (Map.Entry)iterator.next();
            name = String.valueOf(en.getKey());
            value = String.valueOf(en.getValue());
            propertyMap.put(name, value);
        }

        return propertyMap;
    }

    public static Properties loadDefaultConfPropertiesFile()
    {
        return loadPropertiesFile(defaultPropertiesFilePath);
    }

    public static Properties loadPropertiesFile(String path)
    {
        Properties prop = null;
        InputStream is = null;
        try
        {
            System.out.println(new File(path).getAbsolutePath() + "-" + new File(path).getCanonicalPath());
            prop = new Properties();
            is = new FileInputStream(new File(path));

            prop.load(is);
        }
        catch (Exception e)
        {
            System.err.println("Failed to read file: " + path);
            e.printStackTrace();
        }
        return prop;
    }

    public static String getExtensionOfFileName(String fileName)
    {
        int i = fileName.lastIndexOf(".");
        String extension = fileName.substring(i+1);

        return extension;
    }

    public static File convertInputStream2File(InputStream in, String fileName)
    {
        try
        {
            File file = new File(fileName);
            fileName = file.getName();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int bulk;
            while (true)
            {
                bulk = in.read(buffer);
                if (bulk < 0)
                {
                    break;
                }
                fileOutputStream.write(buffer, 0, bulk);
                fileOutputStream.flush();
            }
            fileOutputStream.close();
            in.close();
            return file;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void createFile(OutputStream outPutStream, InputStream inputStream) throws IOException
    {
        byte[] buffer = new byte[1024]; // Adjust if you want
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1)
        {
            outPutStream.write(buffer, 0, bytesRead);
        }
        outPutStream.close();
    }

}
