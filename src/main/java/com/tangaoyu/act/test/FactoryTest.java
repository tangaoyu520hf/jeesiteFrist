package com.tangaoyu.act.test;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Service;

@Service
public class FactoryTest implements FactoryBean<Object> {

	@Override
	public AopTest getObject() throws Exception {
		// TODO Auto-generated method stub
		return new AopTest();
	}

	@Override
	public Class<AopTest> getObjectType() {
		// TODO Auto-generated method stub
		return AopTest.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

}
