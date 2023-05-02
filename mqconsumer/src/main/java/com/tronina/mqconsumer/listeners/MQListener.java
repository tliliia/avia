package com.tronina.mqconsumer.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLConnection;

@Slf4j
@Component
@RequiredArgsConstructor
public class MQListener {
    private final EmailService emailService;

    @RabbitListener(queues = "#{queue.name}", containerFactory = "containerFactory")
    public void onMessage(Message message) {
        try {
            String email = new String(message.getBody());
            log.info("Current thread {} start work with {}", Thread.currentThread().getName(), email);
            emailService.sen
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
