package com.cinema.service.domain.entity;

import com.cinema.service.domain.enums.SeatTypeEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer seatNumber;
    private Boolean available;
    @Enumerated(EnumType.STRING)
    private SeatTypeEnum type;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;
}
