package com.thinkgem.jeesite.common.annotation;

import java.lang.reflect.Method;

import org.springframework.util.ClassUtils;

import com.tangaoyu.act.test.AopTest;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglipProxy implements MethodInterceptor {
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
		System.out.println("代理创建成功");
		System.out.println("操你妈的 臭煞笔");
		System.out.println(proxy.invoke(target, args)+"1");
		return proxy.invoke(target, args);
	}
	
	public static void main(String[] args) {
		CglipProxy proxy = new CglipProxy();
		AopTest aopTest = new AopTest(); 
		proxy.setTarget(aopTest);
		Object createProxy = proxy.createProxy();
		
		MethodInterceptorTest2 interceptorTest2 = new MethodInterceptorTest2();
		interceptorTest2.setTarget(createProxy);
		AopTest aopTest2 = (AopTest) interceptorTest2.createProxy();
		aopTest2.update("123");
	}

}
