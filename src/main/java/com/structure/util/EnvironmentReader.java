package com.structure.util;

import java.io.File;
import java.io.FileInputStream;

import java.util.Map;
import java.util.Properties;

public class EnvironmentReader
{
    public static Properties readEnvironmentVariableProps(String envName)
    {
        Properties environmentVariables = new Properties();
        try
        {
            Map<String, String> map = System.getenv();
            String filename = map.get(envName);
            System.out.println(filename);

            File f = new File(filename);
            if (f.exists())
            {
                FileInputStream in = new FileInputStream(f);
                environmentVariables.load(in);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return environmentVariables;
    }

    public static Properties readEnvironmentVariableProps(String envName, String file)
    {
        Properties environmentVariables = new Properties();
        try
        {
            Map<String, String> map = System.getenv();
            String filename = map.get(envName) + file;
            System.out.println(filename);
            File f = new File(filename);
            if (f.exists())
            {
                FileInputStream in = new FileInputStream(f);
                environmentVariables.load(in);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return environmentVariables;
    }

    public static Properties readJavaVariableProps(String envName, String file)
    {
        Properties environmentVariables = new Properties();
        try
        {
            String path = System.getProperty(envName);

            File f = new File(path + File.separator + file);
            if (f.exists())
            {
                FileInputStream in = new FileInputStream(f);
                environmentVariables.load(in);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return environmentVariables;
    }

    public static Properties readJavaVariableProps(String envName, String addinitionalPath, String file)
    {
        Properties environmentVariables = new Properties();
        try
        {
            String path = System.getProperty(envName);    //real env
            //String path="C:\\conf\\rsdys.appdir";           //local
            File f = new File(path + File.separator + addinitionalPath + File.separator + file);
            if (f.exists())
            {
                FileInputStream in = new FileInputStream(f);
                environmentVariables.load(in);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return environmentVariables;
    }

}
