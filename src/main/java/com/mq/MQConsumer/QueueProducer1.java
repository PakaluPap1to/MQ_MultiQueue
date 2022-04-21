package com.mq.MQConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueueProducer1 {

	 @Value("${qm2.queueManager.queue}")
	    private String queueName;
	
	 @Autowired
	 private JmsTemplate jmsTemplate2;
	 
	
	 
	 public void send(String str) {
		 System.out.println("Working 2");
		 jmsTemplate2.convertAndSend(queueName, "Hi From Producer for QM2 "+str);
	 }

}
