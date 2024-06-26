package com.cinema.service.domain.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.support.converter.SimpleMessageConverter;

public class QueueHandler {
    private static final Logger log = LoggerFactory.getLogger(QueueHandler.class);

    public static <T> T handleQueueMessage(Message message, Class<T> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleMessageConverter converter = new SimpleMessageConverter();
        
        try {
            String messageBody = (String) converter.fromMessage(message);
            log.info("Message of type [" + clazz.getSimpleName() + "] received with correlationId [" +
                    message.getMessageProperties().getCorrelationId() + "]: [" + messageBody + "]");

            return objectMapper.readValue(messageBody, clazz);
        } catch (Exception e) {
            log.error("Error processing message: [" + message.toString() + "]", e);
            return null;
        }
    }
}