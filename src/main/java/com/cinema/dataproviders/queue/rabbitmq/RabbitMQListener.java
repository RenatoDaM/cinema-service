package com.cinema.dataproviders.queue.rabbitmq;

import com.cinema.core.service.TicketService;
import com.cinema.entrypoints.api.dto.TicketDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.cinema.dataproviders.queue.rabbitmq.QueueHandler.handleQueueMessage;

@Component
@RequiredArgsConstructor
public class RabbitMQListener {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQListener.class);

    private final TicketService ticketService;

    @RabbitListener(queues = "ticketQueue")
    public void onMessage(Message ticketMessage) {
        var ticket = handleQueueMessage(ticketMessage, TicketDTO.class);
        ticketService.createTicket(ticket);
    }
}
