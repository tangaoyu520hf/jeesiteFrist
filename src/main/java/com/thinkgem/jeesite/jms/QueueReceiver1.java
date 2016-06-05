package com.thinkgem.jeesite.jms;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;

@Component
public class QueueReceiver1 implements SessionAwareMessageListener<Message>{

	@Override
	public void onMessage(Message message,Session session) {
		final TextMessage createTextMessage  = (TextMessage) message;
		final Session session1 = session;
		try {
			boolean transacted = session.getTransacted();
/*			if(1==1) {
				throw new RuntimeException("123");
			}*/
			
		} catch (JMSException e) {
			e.printStackTrace();
		}
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						FileOutputStream fileOutputStream = new FileOutputStream("D:\\javaSoftwareSteup\\dwa.txt",true);
						Thread.sleep(5000);
						System.out.println(createTextMessage.getText()+"消息来了");
						fileOutputStream.write(createTextMessage.getText().getBytes());
						fileOutputStream.flush();
						session1.commit();
					}  catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("线程执行");
				}
			}).start();
			System.out.println("执行完了哇哈哈啊哇哈哈");
/*		try {
			Thread.sleep(5000);
			System.out.println(createTextMessage.getText()+"消息来了");
			System.out.println("立马执行了");
			session.commit();
		} catch (JMSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
