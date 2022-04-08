package com.example.demo_ibm_mq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
public class MQ1Configuration {

	@Value("${project.mq1.host}")
    private String host;
    @Value("${project.mq1.port}")
    private Integer port;
    @Value("${project.mq1.queue-manager}")
    private String queueManager;
    @Value("${project.mq1.username}")
    private String username;
    @Value("${project.mq1.password}")
    private String password;
    @Value("${project.mq1.channel}")
    private String channel;
    @Value("${project.mq1.receive-timeout}")
    private long receiveTimeout;
    
    @Bean
    public MQQueueConnectionFactory mqQueueConnectionFactory1() {
        MQQueueConnectionFactory mqQueueConnectionFactory1 = new MQQueueConnectionFactory();
        mqQueueConnectionFactory1.setHostName(host);
        try {
            mqQueueConnectionFactory1.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            mqQueueConnectionFactory1.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, false);
            mqQueueConnectionFactory1.setCCSID(1208);
            mqQueueConnectionFactory1.setChannel(channel);
            mqQueueConnectionFactory1.setPort(port);
            mqQueueConnectionFactory1.setQueueManager(queueManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mqQueueConnectionFactory1;
    }
    
    @Bean
    UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter1(MQQueueConnectionFactory mqQueueConnectionFactory1) {
        UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter1 = new UserCredentialsConnectionFactoryAdapter();
        userCredentialsConnectionFactoryAdapter1.setUsername(username);
        userCredentialsConnectionFactoryAdapter1.setPassword(password);
        userCredentialsConnectionFactoryAdapter1.setTargetConnectionFactory(mqQueueConnectionFactory1);
        return userCredentialsConnectionFactoryAdapter1;
    }
    
    @Bean
    public CachingConnectionFactory cachingConnectionFactory1(UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter1) {
        CachingConnectionFactory cachingConnectionFactory1 = new CachingConnectionFactory();
        cachingConnectionFactory1.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter1);
        cachingConnectionFactory1.setSessionCacheSize(500);
        cachingConnectionFactory1.setReconnectOnException(true);
        return cachingConnectionFactory1;
    }
    
    @Bean
    public JmsOperations jmsOperations1(CachingConnectionFactory cachingConnectionFactory1) {
        JmsTemplate jmsTemplate1 = new JmsTemplate(cachingConnectionFactory1);
        jmsTemplate1.setReceiveTimeout(receiveTimeout);
        return jmsTemplate1;
    }
}
