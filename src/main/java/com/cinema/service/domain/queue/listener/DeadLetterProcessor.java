package com.cinema.service.domain.queue.listener;

import com.cinema.service.domain.enums.ExchangeEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeadLetterProcessor {
    private final RabbitTemplate rabbitTemplate;

    private static final String HEADER_X_RETRIES_COUNT = "x-retries-count";
    private static final String HEADER_X_ORIGINAL_EXCHANGE = "x-original-exchange";
    private static final String HEADER_X_ORIGINAL_ROUTING_KEY = "x-original-routing-key";

    private final Logger log = LoggerFactory.getLogger(DeadLetterProcessor.class);

    @RabbitListener(queues = "ticketDeadLetter")
    public void processFailedMessages(Message message) {
        log.info("Received failed message: {}", message.toString());
        processFailedMessagesRetryHeaders(message);
    }

    public void processFailedMessagesRetryHeaders(Message failedMessage) {
        Integer retriesCnt = (Integer) failedMessage.getMessageProperties()
                .getHeaders().get(HEADER_X_RETRIES_COUNT);
        if (retriesCnt == null) retriesCnt = 1;
        if (retriesCnt > 3) {
            log.info("Discarding message");
            return;
        }
        log.info("Retrying message for the {} time", retriesCnt);
        failedMessage.getMessageProperties()
                .getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);
        rabbitTemplate.send(ExchangeEnum.TICKET_EXCHANGE.getQueueName(),
                failedMessage.getMessageProperties().getReceivedRoutingKey(), failedMessage);
    }
}
