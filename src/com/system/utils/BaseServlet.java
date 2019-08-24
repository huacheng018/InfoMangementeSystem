package com.system.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * BaseServlet工具类，用于被子servlet继承
 * @Author: 枠成
 * @Data:2019-08-18
 * @Description:com.system.utils
 * @version:1.0
 */
public class BaseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        doGet(httpServletRequest, httpServletResponse);
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //1、获取请求中method参数的名字
        String methodName = httpServletRequest.getParameter("method");

        /*
        2、获取Class类对象，获取当前servlet程序的Class类对象
        获取的数据类型是Class类对象，并且是存在泛型约束，要求是BaseServlet或者其子类
        */
        Class<? extends BaseServlet> aClass = this.getClass();

        try {
            /*
            3、根据method和Class类对象，获取对应的方法，
            而业务逻辑需要处理的方法都可以认为是service方法，需要的参数都是
            HttpServletRequest和HttpServletResponse
             */
            Method method = aClass.getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);

            /*
            4、执行对应的方法，执行方法的对象时当前BaseServlet对象或者其子类
            执行需要的参数就是HttpServletRequest对象和HttpServletResponse对象
             */
            method.invoke(this, httpServletRequest, httpServletResponse);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
