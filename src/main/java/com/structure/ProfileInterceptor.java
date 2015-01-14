package com.structure;

//import javax.annotation.Resource;
//
//import javax.ejb.EJBContext;
//
//import javax.interceptor.AroundInvoke;
//import javax.interceptor.InvocationContext;
//
//
//public class ProfileInterceptor
//{
//    @Resource
//    EJBContext ctx;
//
//    //private Logger logger = Logger.getLogger(this.getClass());
//
//    @AroundInvoke
//    public Object logUserEvent(InvocationContext invocation) throws Exception
//    {
//        String userName = ctx.getCallerPrincipal().getName();
//        //logger.debug("user " + userName + " has invoked method " + invocation.getMethod().toString());
//        System.out.println("user " + userName + " has invoked method " + invocation.getMethod().toString());
//
//        return invocation.proceed();
//    }
//}
