package com.tronina.avia.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;

public class MQConfig {
    public final static String TOPIC_NAME = "email_topic";
    public final static String DLQ_NAME = "deadLetterQueue";
    public final static String DLQ_EXCHANGE_NAME = "deadLetterExchange";
    public final static String DLQ_ROUTING_KEY = "deadLetter";

        @Bean
        public TopicExchange filesExchange() {
            return new TopicExchange(TOPIC_NAME);
        }

        @Bean
        public Queue deadLetterQueue() {
            return QueueBuilder.durable(DLQ_NAME).build();
        }

        @Bean
        public DirectExchange deadLetterExchange() {
            return new DirectExchange(DLQ_EXCHANGE_NAME);
        }

        @Bean
        public Binding dlqBinding(Queue deadLetterQueue, DirectExchange deadLetterExchange) {
            return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange).with(DLQ_ROUTING_KEY);
        }

        @Bean
        public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory) {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            return container;
        }
}
