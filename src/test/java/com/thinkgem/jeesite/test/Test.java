package com.thinkgem.jeesite.test;


import java.lang.reflect.Method;
import java.util.Map;
import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.servlet.ServletContext;
import javax.validation.groups.Default;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.ProxyAsyncConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.caucho.hessian.server.HessianServlet;
import com.tangaoyu.act.test.ActTest;
import com.tangaoyu.act.test.AopTest;
import com.tangaoyu.act.test.FactoryTest;
import com.thinkgem.jeesite.common.annotation.AdviceTest;
import com.thinkgem.jeesite.common.annotation.AopTestIn;
import com.thinkgem.jeesite.common.annotation.CglipProxy;
import com.thinkgem.jeesite.common.annotation.MybeanProcess;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.User;

public class Test extends Test2 implements InvocationHandler{
	private static int i = 0;  
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	public static void main(String[] args) {
		/*boolean assignableFrom = Connection.class.isAssignableFrom(TopicConnection.class);
		System.out.println(assignableFrom);*/
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring-context.xml");
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();//获取工厂,判断名称是否存在
        if(!beanFactory.containsBean("studentName")){
            BeanDefinitionBuilder beanDefinitionBuilder= BeanDefinitionBuilder.rootBeanDefinition(Student.class);
            beanDefinitionBuilder.addPropertyValue("name", "神一样的男人");
            beanDefinitionBuilder.setInitMethodName("init");
            beanFactory.registerBeanDefinition("studentName", beanDefinitionBuilder.getBeanDefinition());
        }
        @SuppressWarnings("unused")
		BeanDefinition mergedBeanDefinition = beanFactory.getMergedBeanDefinition("studentName");
        Object bean = applicationContext.getBean("studentName");
		System.out.println(bean);
		beanFactory.destroySingleton("studentName");
		beanFactory.destroyBean("studentName");
        Object bean1 = applicationContext.getBean("studentName");
		System.out.println(bean);
		beanFactory.removeBeanDefinition("studentName");

/*		UserDao bean = applicationContext.getBean(UserDao.class);
		bean.updateUserInfo(new User());*/
		/*String[] beanNamesForType = applicationContext.getB
		System.out.println(beanNamesForType);*/
/*		JmsTemplate jmsTemplate  = (JmsTemplate) applicationContext.getBean("jmsQueueTemplate");
		for(int i=0;i<10;i++){
			jmsTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage createTextMessage = session.createTextMessage("神话般的男人"+new Random().nextInt(100));
					 //创建一个名称为HelloWorld的消息队列
		             Queue createQueue = session.createQueue("HelloWorldQueue");
					createTextMessage.setJMSReplyTo(createQueue);
					return createTextMessage;
				}
			});
			System.out.println("来了 他们来了");
		}*/
		/*FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring-context.xml","classpath:spring-context-activeMQ.xml");*/
		/*JmsTemplate jmsTemplate  = (JmsTemplate) applicationContext.getBean("jmsQueueTemplate");
		for(int i=0;i<10;i++){
			jmsTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage createTextMessage = session.createTextMessage("神话般的男人"+new Random().nextInt(100));
					return createTextMessage;
				}
			});
			System.out.println("来了 他们来了");
		}*/
		
		/*		JmsTemplate jmsTemplate  = (JmsTemplate) applicationContext.getBean("jmsQueueTemplate");
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage createTextMessage = session.createTextMessage("神话般的男人");
				return createTextMessage;
			}
		});*/
		//applicationContext.addBeanFactoryPostProcessor(new Mybea);
/*		Object bean2 = applicationContext.getBean("adviceTest");
		System.out.println(bean2);*/
		//AopTest bean = (AopTest) applicationContext.getBean("aopTest");
		//System.out.println(bean.update("dwa"));
/*		InvocationHandler handler = new Test();
		System.out.println(handler);
		Test2 test2 = new Test();
		System.out.println(test2);*/
/*		Map<String, String> map = new HashedMap();
		map.put("123", "dwa");
		System.out.println(map);*/
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
/*		 //连接工厂
        ConnectionFactory connectionFactory;
        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session;
        //消息的目的地
        Destination destination;
        //消息生产者
        MessageProducer messageProducer;
        //实例化连接工厂
        connectionFactory = new org.apache.activemq.ActiveMQConnectionFactory(BROKEURL);
        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            //创建一个名称为HelloWorld的消息队列
            destination = session.createQueue("HelloWorldQueue");
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //创建一条文本消息 
            TextMessage message = session.createTextMessage("ActiveMQ 发送消息" +i);
            message.setJMSReplyTo(destination);
            System.out.println("发送消息：Activemq 发送消息" + i);
            //通过消息生产者发出消息 
            messageProducer.send(message);
            session.commit();
            System.out.println("神话");

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }*/
		
/*		ConnectionFactory connectionFactory;//连接工厂
        Connection connection = null;//连接

        Session session;//会话 接受或者发送消息的线程
        Destination destination;//消息的目的地

        MessageConsumer messageConsumer;//消息的消费者//实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(BROKEURL);

        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            //创建一个连接HelloWorld的消息队列
            destination = session.createQueue("HelloWorld");
            //创建消息消费者
            messageConsumer = session.createConsumer(destination);

            while (true) {
                TextMessage textMessage = (TextMessage) messageConsumer.receive(100000);
                if(textMessage != null){
                    System.out.println("收到的消息:" + textMessage.getText());
                }else {
                    break;
                }
            }


        } catch (JMSException e) {
            e.printStackTrace();
        }*/
	}
	@org.junit.Test
	public void test(){
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring-context.xml","classpath:spring-context-activeMQ.xml");
		JmsTemplate jmsTemplate  = (JmsTemplate) applicationContext.getBean("jmsTopicTemplate");
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage createTextMessage = session.createTextMessage("主题-神话般的男人");
				return createTextMessage;
			}
		});
	}
	
	@org.junit.Test
	public void test2(){
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring-context.xml","classpath:spring-context-activeMQ.xml");
		JmsTemplate jmsTemplate  = (JmsTemplate) applicationContext.getBean("jmsQueueTemplate");
		for(int i=0;i<10;i++){
			jmsTemplate.send(new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage createTextMessage = session.createTextMessage("神话般的男人"+new Random().nextInt(100));
					 //创建一个名称为HelloWorld的消息队列
		             Queue createQueue = session.createQueue("HelloWorldQueue");
					createTextMessage.setJMSReplyTo(createQueue);
					return createTextMessage;
				}
			});
			System.out.println("来了 他们来了");
		}
		try {
			Thread.sleep(8000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@org.junit.Test
	public void test3(){
		 //连接工厂
        ConnectionFactory connectionFactory;
        //连接
        Connection connection = null;
        //会话 接受或者发送消息的线程
        Session session;
        //消息的目的地
        Destination destination;
        //消息生产者
        MessageProducer messageProducer;
        //实例化连接工厂
        connectionFactory = new org.apache.activemq.ActiveMQConnectionFactory(BROKEURL);
        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            //启动连接
            connection.start();
            //创建session
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            //创建一个名称为HelloWorld的消息队列
            destination = session.createQueue("HelloWorldQueue");
            //创建消息生产者
            messageProducer = session.createProducer(destination);
            //创建一条文本消息 
            TextMessage message = session.createTextMessage("ActiveMQ 发送消息" +i);
            message.setJMSReplyTo(destination);
            System.out.println("发送消息：Activemq 发送消息" + i);
            //通过消息生产者发出消息 
            messageProducer.send(message);
            session.commit();
            System.out.println("神话");

        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
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
	public static void addMongoToBeanFactory(Class<?> beanClass,String beanName,String host,int port,String database){
 
}
	
}


