package com.cinema.service.domain.queue.configuration;

import com.cinema.service.domain.enums.ExchangeEnum;
import com.cinema.service.domain.enums.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Bean
    public Queue ticketQueue() {
        return QueueBuilder.durable(QueueEnum.TICKET_QUEUE.getQueueName())
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QueueEnum.TICKET_DEAD_LETTER.getQueueName())
                .build();
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable(QueueEnum.TICKET_DEAD_LETTER.getQueueName()).build();
    }

    @Bean
    DirectExchange messagesExchange() {
        return new DirectExchange(ExchangeEnum.TICKET_EXCHANGE.getQueueName());
    }

    @Bean
    Binding bindingMessages() {
        return BindingBuilder.bind(ticketQueue())
                .to(messagesExchange())
                .with(QueueEnum.TICKET_QUEUE
                        .getQueueName());
    }
}