package com.structure.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Properties;

import com.structure.vo.MapObject;


public class ConversionUtil
{
    public static InputStream convertFileToInputStream(File file) throws FileNotFoundException, IOException
    {
        ByteArrayInputStream bais = null;
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            String content = "";
            while ((line = bufferedReader.readLine()) != null)
            {
                content = content + line;
            }
            bais = new ByteArrayInputStream(content.getBytes());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return bais;
    }

    public static byte[] convertInputStreamToByteArray(InputStream inputStream) throws IOException
    {
        //byte[] file = org.apache.commons.io.IOUtils.toByteArray(inputStream);
        //if apache jar not exist below snippet works
        byte[] buffer = new byte[8192];
        int bytesRead;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((bytesRead = inputStream.read(buffer)) != -1)
        {
            output.write(buffer, 0, bytesRead);
        }
        return output.toByteArray();
    }
    
    public static InputStream convertByteArrayToInputStream(byte[] file)
    {
        return new ByteArrayInputStream(file);
    }
    
    public static Properties convertMapObjectsToProperties(Collection<MapObject> mapObjectList)
    {
        Properties prop=new Properties();
        for(MapObject object:mapObjectList)
            prop.put(object.getName(), object.getValue());
        
        return prop;
    }
}

