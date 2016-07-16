package com.example.gaomin.invokedemo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by gaomin on 2016/6/28.
 */
public class ListenerInvocationHandler implements InvocationHandler{
    //目标对象
    private Object target;
    private HashMap<String,Method> methodMap = new HashMap<>();


    public ListenerInvocationHandler(Object target){
        this.target = target;
    }

    //onClick方法执行之前要执行的方法
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(target != null){
            //持行方法名称onClick
            String methodName = method.getName();
            //toast
            method = methodMap.get(methodName);
            if(method !=null){
                return method.invoke(target,args);
            }
        }
        return null;
    }
    /*
     *绑定方法一一对应
    * */
    public void addMethod(String name,Method method){
       methodMap.put(name,method);
    }
}
