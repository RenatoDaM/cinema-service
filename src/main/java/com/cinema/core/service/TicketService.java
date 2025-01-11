package com.cinema.core.service;

import com.cinema.core.domain.entity.Seat;
import com.cinema.core.domain.entity.Ticket;
import com.cinema.entrypoints.api.dto.TicketDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final SeatService seatService;
    private static final Logger log = LoggerFactory.getLogger(TicketService.class);

    public void createTicket(TicketDTO ticketDTO) {
        Seat seatEntity = seatService.findById(ticketDTO.getSeatId());

        Ticket ticketEntity = new Ticket(
                null, ticketDTO.getUuid(),
                ticketDTO.getPaidPrice(),
                ticketDTO.getDiscountType()
        );

        seatEntity.setTicket(ticketEntity);
        log.info("saving ticket [{}]", ticketEntity);
        seatService.updateSeat(seatEntity);
    }
}
