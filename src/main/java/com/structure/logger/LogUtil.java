package com.structure.logger;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.structure.BaseEntity;


public class LogUtil
{
    public static final char PERSIST = 'P';
    public static final char UPDATE = 'U';
    public static final char REMOVE = 'R';

    private static int limitForLog = 1000;

    public static LogUtil logUtil = new LogUtil();

    private List<PreLoggedProcess> processList;

    private LogUtil()
    {
        processList = Collections.synchronizedList(new ArrayList<PreLoggedProcess>());
    }

    public static LogUtil getInstance()
    {
        return logUtil;
    }

    public void logProcess(BaseEntity entity, char process, String userName)
    {
        addProcess(new PreLoggedProcess(Thread.currentThread().getStackTrace(), entity, process, userName));
    }

    public static LoggedProcess createLoggedProcess(PreLoggedProcess preLoggedProcess)
    {
        LoggedProcess loggedProcess = null;

        Logged logged = null;

        for (StackTraceElement element : preLoggedProcess.getElements())
        {
            //will be logged only if class in below package
            if (element.getClassName().indexOf("com.accmee.structure.sessionbean") == 0)
            {
                try
                {
                    Method[] methods = Class.forName(element.getClassName()).getDeclaredMethods();
                    for (Method method : methods)
                    {
                        if (method.getName().indexOf(element.getMethodName()) > -1)
                        {
                            //will be logged only if method is annotated with Logged
                            if (method.getAnnotation(Logged.class) == null)
                                continue;

                            loggedProcess = new LoggedProcess();

                            loggedProcess.setUser(preLoggedProcess.getUserName());
                            loggedProcess.setProcess(preLoggedProcess.getProcess());
                            loggedProcess.setMethod(method.getName());
                            loggedProcess.setVersion((int)preLoggedProcess.getEntity().getVersion());
                            loggedProcess.setEntityName(preLoggedProcess.getEntity().getClass().toString());
                            loggedProcess.setEntityId(preLoggedProcess.getEntity().getId());
                            loggedProcess.setEntityValue(preLoggedProcess.getEntity().toString());
                            loggedProcess.setDateOfProcess(new Date());
                        }
                    }
                }
                catch (Exception exc)
                {
                    return null;
                }
            }
        }

        return loggedProcess;
    }

    private void addProcess(PreLoggedProcess preLoggedProcess)
    {
        if (processList.size() > limitForLog)
            processList.remove(0);

        processList.add(preLoggedProcess);
    }

    public void getProcessList(Collection collection)
    {
        collection.addAll(processList);
        processList.removeAll(collection);
    }
}
