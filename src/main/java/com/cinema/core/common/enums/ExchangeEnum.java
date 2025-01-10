package com.cinema.core.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExchangeEnum {
    TICKET_EXCHANGE("ticketExchange");
    private final String queueName;
}