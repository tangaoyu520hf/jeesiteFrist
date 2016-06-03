package com.thinkgem.jeesite.common.annotation;
import java.lang.reflect.InvocationHandler;  
import java.lang.reflect.Method;  
  
import sun.reflect.Reflection;  
  
public class MyProxy implements InvocationHandler {  
  
    private Object obj;  
  
    private String name;  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
  
    public Object getObj() {  
        return obj;  
    }  
  
    public void setObj(Object obj) {  
        this.obj = obj;  
    }  
  
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {  
        System.out.println("begin================" + "bean 名称为【" + name + "】方法为【" + method.getName() + "】========="  
                + obj.getClass());  
        return method.invoke(obj, args);  
    }  
} 