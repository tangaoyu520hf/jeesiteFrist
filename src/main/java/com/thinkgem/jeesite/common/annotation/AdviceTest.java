package com.thinkgem.jeesite.common.annotation;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AdviceTest implements MethodInterceptor {

	public AdviceTest() {
		System.out.println("通知初始化了");
	}
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		invocation.getArguments();
		System.out.println("自定义的执行了");
		return invocation.proceed();
	}

}
