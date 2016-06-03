package com.thinkgem.jeesite.common.annotation;

import java.lang.reflect.Method;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
	   

import org.springframework.stereotype.Service;

//@Service
public class MybeanProcess implements BeanPostProcessor {
	@Override     
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		System.out.println(456);
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		for(Method method :bean.getClass().getMethods()){
			System.out.println(method.getName());
		}
		System.out.println(123);
/*		MyProxy myProxy = new MyProxy();
		myProxy.setObj(bean);*/
		CglipProxy myProxy = new CglipProxy();
		myProxy.setTarget(bean);
		Object createProxy = myProxy.createProxy();
		Method[] methods = createProxy.getClass().getMethods();
		for(Method method :methods){
			System.out.println(method.getName());
		}
		return null;
	}

}
