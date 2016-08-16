package com.thinkgem.jeesite.test;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.SynchronousQueue;

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
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.validation.groups.Default;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.executor.loader.cglib.CglibProxyFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.AsyncAnnotationBeanPostProcessor;
import org.springframework.scheduling.annotation.ProxyAsyncConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;
import org.springframework.util.ResourceUtils;
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
import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.modules.cms.entity.Article;
import com.thinkgem.jeesite.modules.sys.dao.RoleDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.Role;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.test.Test.B;

public class Test implements InvocationHandler{
	private static String GBK="";
	
	private static int i = 0;  
	private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;
	static CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<String>();
	
	public static void test6(){
		/*System.out.println(arrayList.size());
		System.out.println(arrayList.size());*/
		new SQL() {{
		    SELECT("P.ID, P.USERNAME, P.PASSWORD, P.FULL_NAME");
		    SELECT("P.LAST_NAME, P.CREATED_ON, P.UPDATED_ON");
		    FROM("PERSON P");
		    FROM("ACCOUNT A");
		    INNER_JOIN("DEPARTMENT D on D.ID = P.DEPARTMENT_ID");
		    INNER_JOIN("COMPANY C on D.COMPANY_ID = C.ID");
		    WHERE("P.ID = A.ID");
		    WHERE("P.FIRST_NAME like ?");
		    OR();
		    WHERE("P.LAST_NAME like ?");
		    GROUP_BY("P.ID");
		    HAVING("P.LAST_NAME like ?");
		    OR();
		    HAVING("P.FIRST_NAME like ?");
		    ORDER_BY("P.ID");
		    ORDER_BY("P.FULL_NAME");
		  }}.toString();
	}
	
	public static void main(String[] args) {
		test6();
		String str = new StringBuilder().append("dwa").append("dddd").toString();
		System.out.println(str.intern()==str);
		String str1 = new StringBuilder().append("java").append("va").toString();
		String str2 = "javava";
		System.out.println(str1.intern()==str1);
		System.out.println(str1.intern()==str2);
		/*boolean assignableFrom = Connection.class.isAssignableFrom(TopicConnection.class);
		System.out.println(assignableFrom);*/
/*		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring-context.xml");
		String[] beanNamesForType = applicationContext.getBeanNamesForType(Object.class);
		System.out.println(beanNamesForType);*/
		
		
/*		try {
			File resourceAsFile = Resources.getResourceAsFile("com/initData/com");
			for(File file :  resourceAsFile.listFiles()){
				System.out.println(file);
			}
			ArrayList<URL> list = Collections.list(Test.class.getClassLoader().getResources("com/initData/com"));
			for(URL url : list){
				InputStream openStream = url.openStream();
	            BufferedReader reader = new BufferedReader(new InputStreamReader(openStream));
	            List<String> lines = new ArrayList<String>();
	            for (String line; (line = reader.readLine()) != null;) {
	              lines.add(line);
	            }
	            System.out.println(lines);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		ClassPathResource  classPathResource = new ClassPathResource("spring-context.xml");
	    DefaultListableBeanFactory  beanFactory = new DefaultListableBeanFactory();
	    XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
	    reader.loadBeanDefinitions(classPathResource);
	    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) beanFactory.getBean("sqlSessionFactory");
	    SqlSession openSession = sqlSessionFactory.openSession();
	    RoleDao mapper = openSession.getMapper(RoleDao.class);
	    mapper.getAreainfos();
	    Role role = mapper.get("1");
	    System.out.println(role);
/*		CyclicBarrier barrier = new CyclicBarrier(2,new Runnable() {
			@Override
			public void run() {
				System.out.println("成功执行");
			}
		});
		try {
			barrier.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
			
			
		
		arrayList.add("123456");
		System.out.println(Test.arrayList);
		/*ExecutorService;FutureTask;CountDownLatch<E>;Executors;Semaphore;CyclicBarrier*/
		
		
/*		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		MimeMessage createMimeMessage = javaMailSenderImpl.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(createMimeMessage);*/
		/*helper.addAttachment(attachmentFilename, inputStreamSource);*/
		/*
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
		beanFactory.removeBeanDefinition("studentName");*/

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
	public void test(List<? super B> list) throws InterruptedException{
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring-context.xml","classpath:spring-context-activeMQ.xml");
		JmsTemplate jmsTemplate  = (JmsTemplate) applicationContext.getBean("jmsTopicTemplate");
		System.out.println(1234567);
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage createTextMessage = session.createTextMessage("主题-神话般的男人");
				return createTextMessage;
			}
		});
		Thread.sleep(5000);
		System.out.println(123456);
		Map<?,Object> map = new HashedMap();
		map.put(null, "");
		
	}
	
	class A{
		public final void name() {
			System.out.println(123);
		}
		
	}
	
	class B extends A{
		
		
	}
	
	@org.junit.Test
	public void test2(){
		List<A> list = new ArrayList<A>();
		list.add(new A());
		
		List<? super B> list1 = new ArrayList<B>();
		 
		new B().name();
		list1.get(0);
		
		try {
			test(list);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext("classpath:spring-context.xml","classpath:spring-context-activeMQ.xml");
		JmsTemplate jmsTemplate  = (JmsTemplate) applicationContext.getBean("jmsQueueTemplate");
		System.out.println(123456);
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
			System.out.println(123456);
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


