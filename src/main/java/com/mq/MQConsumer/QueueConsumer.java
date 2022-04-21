package com.mq.MQConsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {
	
	@Autowired
	private JmsTemplate jmsTemplate2;
	
	@Autowired
	private QueueProducer1 producer1;
	

    @JmsListener(destination = "${qm1.queueManager.queue}", containerFactory = "qm1JmsListenerContainerFactory")
    public void receive1(String text) {
        System.out.println("Received from qm1: " + text);
        producer1.send(text);
    }

    @JmsListener(destination = "${qm2.queueManager.queue}", containerFactory = "qm2JmsListenerContainerFactory")
    public void receive2(String text) {
        System.out.println("Received from qm4: " + text);
    }

}
