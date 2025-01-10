package com.cinema.dataproviders.queue.rabbitmq;

import com.cinema.core.common.enums.ExchangeEnum;
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
            log.info("discarding message with correlationId: {}", failedMessage.getMessageProperties().getCorrelationId());
            return;
        }
        log.info("Retrying message with correlation id [{}] for the [{}] time", failedMessage.getMessageProperties().getCorrelationId(), retriesCnt);
        failedMessage.getMessageProperties()
                .getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCnt);
        rabbitTemplate.send(ExchangeEnum.TICKET_EXCHANGE.getQueueName(),
                failedMessage.getMessageProperties().getHeader("x-last-death-queue"), failedMessage);
    }
}
