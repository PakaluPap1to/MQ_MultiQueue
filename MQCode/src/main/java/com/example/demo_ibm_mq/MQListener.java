package com.example.demo_ibm_mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MQListener {
	@Autowired
	IBMMQProducer1 producer1;
	
	@JmsListener(destination = "${project.mq.queue-name}")
	public void receiveMessage(final Message jsonMessage) throws JMSException{
		String messageData = null;
		if(jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)jsonMessage;
			messageData = textMessage.getText();
			System.out.println(messageData+" From Papa 1");
			//producer1.send(messageData);
		}
	}
	
	@JmsListener(destination = "${project.mq1.queue-name}")
	public void receiveMessage1(final Message jsonMessage) throws JMSException{
		String messageData = null;
		if(jsonMessage instanceof TextMessage) {
			TextMessage textMessage = (TextMessage)jsonMessage;
			messageData = textMessage.getText();
			System.out.println(messageData+" From Papa 2");
		}
	}
}
