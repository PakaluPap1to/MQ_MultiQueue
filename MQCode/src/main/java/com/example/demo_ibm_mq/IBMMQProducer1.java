package com.example.demo_ibm_mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

@Component
public class IBMMQProducer1 {

	@Value("${project.mq1.queue-name}")
    private String queueName;
	
	@Autowired
	private JmsOperations jmsOperations1;
	
    public String send(String str){
		jmsOperations1.convertAndSend(queueName, "Helluuuuuu Rohan from MQ2 "+str);
        return "Send message successfully";
    }
	
}
