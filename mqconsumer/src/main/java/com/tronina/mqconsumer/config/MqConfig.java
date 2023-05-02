package com.tronina.mqconsumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.akvelon.mqconsumer.config.MqConstants.FILES_BINDING_KEY;

@Configuration
public class MqConfig {
    public final static String DLQ_EXCHANGE_NAME = "deadLetterExchange";
    public final static String DLQ_ROUTING_KEY = "deadLetter";

    public static final String TOPIC_NAME = "email_topic";
    public static final String BINDING_KEY = "email.#";

    @Bean
    public TopicExchange filesExchange() {
        return new TopicExchange(TOPIC_NAME);
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable()
                .withArgument("x-dead-letter-exchange", DLQ_EXCHANGE_NAME)
                .withArgument("x-dead-letter-routing-key", DLQ_ROUTING_KEY)
                .autoDelete()
                .build();
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange filesExchange) {
        return BindingBuilder.bind(queue).to(filesExchange).with(BINDING_KEY);
    }

    @Bean
    public RabbitListenerContainerFactory<SimpleMessageListenerContainer>
    containerFactory(ConnectionFactory rabbitConnectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setPrefetchCount(10);
        factory.setConcurrentConsumers(1);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
}
