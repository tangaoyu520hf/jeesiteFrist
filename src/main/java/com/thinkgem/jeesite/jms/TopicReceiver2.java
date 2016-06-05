package com.thinkgem.jeesite.jms;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

@Component
public class TopicReceiver2 implements MessageListener {

	@Override
	public void onMessage(Message message) {
		System.out.println(message);
	}
	
}
