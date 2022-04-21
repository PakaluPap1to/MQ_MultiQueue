package com.mq.MQConsumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(0)
public class QueueProducer {
	
	@Value("${qm1.queueManager.queue}")
    private String queueName;
	
	 @Autowired
	 private JmsTemplate jmsTemplate1;
	 
	 @Autowired
	 private QM1Config config;
	 
	 public void send() {
		 System.out.println("Working 1");
		 jmsTemplate1.convertAndSend(queueName, "Hi From Producer for QM1");
	 }

}
