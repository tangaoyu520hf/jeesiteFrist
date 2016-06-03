package com.thinkgem.jeesite.common.annotation;

import java.lang.reflect.Method;

import org.springframework.util.ClassUtils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class MethodInterceptorTest2 implements MethodInterceptor {
	private Object target;
	

	public Object getTarget() {
		return target;
	}


	public void setTarget(Object target) {
		this.target = target;
	}
	
	public Object createProxy(){
		Enhancer enhancer = new Enhancer();
		if(ClassUtils.isCglibProxyClass(target.getClass()))
			enhancer.setSuperclass(target.getClass().getSuperclass());
		else
			enhancer.setSuperclass(target.getClass());
		enhancer.setCallback(this);
		return enhancer.create();
	}
	
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		System.out.println("2执行了");
		System.out.println(proxy.invoke(target, args));
		return proxy.invoke(target, args);
	}

}
