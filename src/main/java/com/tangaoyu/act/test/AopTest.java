package com.tangaoyu.act.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AopTest {
	
	public AopTest(){
		System.out.println(123);
	}
	
	
	@Transactional(readOnly=false)
	public Object update(String str){
		/*AnnotationAwareAspectJAutoProxyCreator*/
		System.out.println(str);
		return "";
		/*//Object currentProxy = AopContext.currentProxy();
		//Field[] declaredFields = currentProxy.getClass().getDeclaredFields();
		for(Field method : declaredFields){
			System.out.println(method.getName());
		}
		return currentProxy;*/
	}
}
