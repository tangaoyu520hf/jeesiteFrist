package com.thinkgem.jeesite.jms;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * The Supplier synchronously receives the order from the Vendor and
 * randomly responds with either the number ordered, or some lower
 * quantity. 
 */
public class Supplier implements Runnable {
	private String url;
	private String user;
	private String password;
	private final String ITEM;
	private final String QUEUE;
	
	public Supplier(String item, String queue, String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.ITEM = item;
		this.QUEUE = queue;
	}
	
	public void run() {
		System.out.println(123456);
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		Session session = null;
		Destination orderQueue;
		try {
			Connection connection = connectionFactory.createConnection();

			session = connection.createSession(true, Session.SESSION_TRANSACTED);
			orderQueue = session.createQueue(QUEUE);
			MessageConsumer consumer = session.createConsumer(orderQueue);
			
			connection.start();
			
			while (true) {
				Message message = consumer.receive();
				System.out.println(message);
				MessageProducer producer = session.createProducer(session.createQueue("HelloWorldQueue"));
				MapMessage orderMessage;
				if (message instanceof MapMessage) {
					orderMessage = (MapMessage) message;
				} else {
					Thread.sleep(500);
					//session.rollback();
					// End of Stream
					producer.send(session.createTextMessage("回复来了"));
					session.commit();
					producer.close();
					break;
				}
			}
			System.out.println("执行完毕");
			connection.close();
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String url = "tcp://localhost:61616";
		String user = null;
		String password = null;
		String item = "HardDrive";
		
		if (args.length >= 1) {
			item = args[0];
		}
		String queue;
		if ("HardDrive".equals(item)) {
			queue = "StorageOrderQueue";
		} else if ("Monitor".equals(item)) {
			queue = "MonitorOrderQueue";
		} else {
			throw new IllegalArgumentException("Item must be either HardDrive or Monitor");
		}
		
		if (args.length >= 2) {
			url = args[1];
		}
		
		if (args.length >= 3) {
			user = args[2];
		}

		if (args.length >= 4) {
			password = args[3];
		}
		
		Supplier s = new Supplier(item, queue, url, user, password);
		
		new Thread(s, "Supplier " + item).start();
	}
}
