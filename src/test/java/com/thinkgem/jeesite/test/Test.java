package com.thinkgem.jeesite.test;


import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.validation.groups.Default;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.ProxyAsyncConfiguration;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.caucho.hessian.server.HessianServlet;
import com.tangaoyu.act.test.ActTest;
import com.tangaoyu.act.test.AopTest;
import com.tangaoyu.act.test.FactoryTest;
import com.thinkgem.jeesite.common.annotation.AdviceTest;
import com.thinkgem.jeesite.common.annotation.AopTestIn;
import com.thinkgem.jeesite.common.annotation.CglipProxy;
import com.thinkgem.jeesite.common.annotation.MybeanProcess;

public class Test extends Test2 implements InvocationHandler{
	private static int i = 0;  
	public static void main(String[] args) {
		//FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring-context.xml");
		//applicationContext.addBeanFactoryPostProcessor(new Mybea);
/*		Object bean2 = applicationContext.getBean("adviceTest");
		System.out.println(bean2);*/
		//AopTest bean = (AopTest) applicationContext.getBean("aopTest");
		//System.out.println(bean.update("dwa"));
/*		InvocationHandler handler = new Test();
		System.out.println(handler);
		Test2 test2 = new Test();
		System.out.println(test2);*/
		Map<String, String> map = new HashedMap();
		map.put("123", "dwa");
		System.out.println(map);
/*		
		Object bean = applicationContext.getBean("aopTest");
		Object bean2 = applicationContext.getBean("aopTest");
		System.out.println(bean==bean2);
		BeanWrapperImpl beanWrapperImpl = new BeanWrapperImpl();
		beanWrapperImpl.setWrappedInstance(new Student());
		Object propertyValue = beanWrapperImpl.getPropertyValue("studentSon.name");
		
		System.out.println(propertyValue);*/
/*		Object bean = applicationContext.getBean("factoryTest");
		System.out.println(bean);*/
		//HotSwappableTargetSource
		//AnnotationAwareAspectJAutoProxyCreator
		//AopProxy   BeanFactory
		//ProxyFactoryBean ProxyFactory
/*		ClassPathResource classPathResource = new ClassPathResource("spring-context.xml");
		ConfigurableBeanFactory factory = new XmlBeanFactory(classPathResource);  
		factory.addBeanPostProcessor(new MybeanProcess());  
		Object bean = factory.getBean("aopTest"); 
		System.out.println(bean);*/
/*		Student student = new Student();
		Student test = student.getTest(i);
		System.out.println(test);*/
		/*BeanNameAutoProxyCreator*/
		//BeanFactory
		//TransactionProxyFactoryBean
		//HessianServlet
		//
		
	}
	@org.junit.Test
	public void test(){
		
	}
	
	
	private static int getTest(){
		if(i<2){
			i++;
			return getTest();
		}
		return i;
	} 
	
	public class Employee
	{
		   public Employee() {
			   Test.this.getTest();
			   System.out.println(this);
		}
	       Company company;
	       Card card;
	       String name;
	       String id;
	}
	class Company
	{
	        String companyName;
	        Map<String,String> attrs;
	}
	class Card
	{
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		return null;
	}
	
}

class Shenhuan{
	
}


