package com.cinema.service.rest.dto;

import com.cinema.service.domain.enums.DiscountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketDTO implements Serializable {
    private Long seatId;
    private String uuid;
    private BigDecimal paidPrice;
    private Integer seatNumber;
    private DiscountTypeEnum discountType;
}
