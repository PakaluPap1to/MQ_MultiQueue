package com.example.demo_ibm_mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
public class IBMMQProduer {
	
	@Value("${project.mq.queue-name}")
    private String queueName;
	
	@Autowired
	private JmsOperations jmsOperations;
	
    public String send(String str){
		jmsOperations.convertAndSend(queueName, "Helluuuuuu Rohan from MQ1 "+str);
        return "Send message successfully";
    }
}
