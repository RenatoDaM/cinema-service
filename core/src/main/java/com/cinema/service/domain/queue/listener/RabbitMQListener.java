package com.cinema.service.domain.queue.listener;

import com.cinema.service.domain.queue.QueueHandler;
import com.cinema.service.rest.dto.TicketDTO;
import com.cinema.service.domain.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.cinema.service.domain.queue.QueueHandler.handleQueueMessage;

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
