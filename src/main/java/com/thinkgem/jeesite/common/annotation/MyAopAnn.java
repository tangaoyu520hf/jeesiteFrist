package com.thinkgem.jeesite.common.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/*@Component
@Aspect*/
public class MyAopAnn extends MyAopAnn3 {

	@Pointcut("execution(* com.tangaoyu.act.test.*.*(..))")
	private void aspectjMethod(){};  
	
	@Around("aspectjMethod()")
	public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
		System.out.println("注解的执行了");
		return "2";
	}
}
