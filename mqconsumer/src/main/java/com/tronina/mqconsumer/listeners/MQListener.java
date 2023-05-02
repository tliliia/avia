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
public class MQListener {

    @RabbitListener(queues = "#{queue.name}", containerFactory = "containerFactory")
    public void onMessage(Message message) {
        try {
            String fileUrl = new String(message.getBody());
            log.info("Current thread {} start work with {}", Thread.currentThread().getName(), fileUrl);
            URL url = new URL(fileUrl);

            URLConnection urlConnection = url.openConnection();
            long size = urlConnection.getContentLength();
            log.info("File {} size is {}", fileUrl, size);

            if (size == -1) {
                throw new AmqpRejectAndDontRequeueException(fileUrl);
            }
        } catch (Exception e) {
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
