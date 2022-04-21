package com.mq.MQConsumer;

import java.util.List;
import java.util.Optional;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.mq.spring.boot.MQConfigurationProperties;
import com.ibm.mq.spring.boot.MQConnectionFactoryCustomizer;
import com.ibm.mq.spring.boot.MQConnectionFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
public class QM1Config {

    @Bean
    @ConfigurationProperties("qm1")
    public MQConfigurationProperties qm1ConfigProperties() {
        return new MQConfigurationProperties();
    }

    @Bean
    public MQConnectionFactory qm1ConnectionFactory(@Qualifier("qm1ConfigProperties") MQConfigurationProperties properties, ObjectProvider<List<MQConnectionFactoryCustomizer>> factoryCustomizers) throws JMSException {
    	MQConnectionFactory mqQueueConnectionFactory =  new MQConnectionFactoryFactory(properties, factoryCustomizers.getIfAvailable()).createConnectionFactory(MQConnectionFactory.class);
//    	mqQueueConnectionFactory.setClientReconnectOptions(WMQConstants.WMQ_CLIENT_RECONNECT);
//    	mqQueueConnectionFactory.setClientReconnectTimeout(1800);
    	//mqQueueConnectionFactory.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, "TLS_RSA_WITH_AES_128_CBC_SHA256");
		//mqQueueConnectionFactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
    	return mqQueueConnectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> qm1JmsListenerContainerFactory(@Qualifier("qm1ConnectionFactory") ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
//    
//    @Bean
//    public CachingConnectionFactory cachingConnectionFactory(@Qualifier("qm1ConnectionFactory") ConnectionFactory connectionFactory) {
//    	CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//        cachingConnectionFactory.setTargetConnectionFactory(connectionFactory);
//        cachingConnectionFactory.setSessionCacheSize(500);
//        cachingConnectionFactory.setReconnectOnException(true);
//        return cachingConnectionFactory;
//    }
//    
    @Bean("jmsTemplate1")
    public JmsTemplate jmsTemplate(@Qualifier("qm1ConnectionFactory") MQConnectionFactory qm1ConnectionFactory) {
    	JmsTemplate jmsTemplate1  = new JmsTemplate(qm1ConnectionFactory);
    	return jmsTemplate1;
    }


}
