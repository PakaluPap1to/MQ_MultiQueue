package com.mq.MQConsumer;

import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
public class QM2Config {

    @Bean
    @ConfigurationProperties("qm2")
    public MQConfigurationProperties qm2ConfigProperties() {
        return new MQConfigurationProperties();
    }

    @Bean
    public MQConnectionFactory qm2ConnectionFactory(@Qualifier("qm2ConfigProperties") MQConfigurationProperties properties, ObjectProvider<List<MQConnectionFactoryCustomizer>> factoryCustomizers) throws JMSException {
    	MQConnectionFactory mqQueueConnectionFactory = new MQConnectionFactoryFactory(properties, factoryCustomizers.getIfAvailable()).createConnectionFactory(MQConnectionFactory.class);
//    	mqQueueConnectionFactory.setClientReconnectOptions(WMQConstants.WMQ_CLIENT_RECONNECT);
//    	mqQueueConnectionFactory.setClientReconnectTimeout(1800);
    	//mqQueueConnectionFactory.setStringProperty(WMQConstants.WMQ_SSL_CIPHER_SUITE, "TLS_RSA_WITH_AES_128_CBC_SHA256");
		//mqQueueConnectionFactory.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
		return mqQueueConnectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> qm2JmsListenerContainerFactory(@Qualifier("qm2ConnectionFactory") ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) throws InterruptedException {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        return factory;
    }
//    
//    @Bean
//    public CachingConnectionFactory cachingConnectionFactory2(@Qualifier("qm2ConnectionFactory") ConnectionFactory connectionFactory) {
//    	CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//        cachingConnectionFactory.setTargetConnectionFactory(connectionFactory);
//        cachingConnectionFactory.setSessionCacheSize(500);
//        cachingConnectionFactory.setReconnectOnException(true);
//        return cachingConnectionFactory;
//    }
//    
    @Bean("jmsTemplate2")
    public JmsTemplate jmsTemplate(@Qualifier("qm2ConnectionFactory") MQConnectionFactory qm2ConnectionFactory) {
    	JmsTemplate jmsTemplate2  = new JmsTemplate(qm2ConnectionFactory);
    	return jmsTemplate2;
    }

}