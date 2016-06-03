package com.thinkgem.jeesite.common.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Aspect
public class MyAopAnn3 {
	public MyAopAnn3(){
		
	}
	public MyAopAnn3(String str){
		
	}
	@Pointcut("execution(* com.tangaoyu.act.test.*.*(..))")
	private void aspectjMethod(){};  
	
	@Around("aspectjMethod()")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		proceedingJoinPoint.getArgs();
		System.out.println(proceedingJoinPoint);
		Object target = proceedingJoinPoint.getTarget();
		return proceedingJoinPoint.proceed();
	}
}
