package com.cinema.service.domain.queue.error;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

@Configuration
public class CustomErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
            throw new AmqpRejectAndDontRequeueException("testing...", t);
        }
}