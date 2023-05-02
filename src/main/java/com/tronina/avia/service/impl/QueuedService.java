package com.tronina.avia.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueuedService {
    private final static String ROUTING_NAME = "email_topic";
    private final static String ROUTING_KEY = "email";
    private final RabbitTemplate rabbitTemplate;


    public void produceReportAndSend(String email) {
        rabbitTemplate.send(ROUTING_NAME, ROUTING_KEY, new Message(email.getBytes()));
    }
}
