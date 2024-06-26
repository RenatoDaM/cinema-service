package com.cinema.service.rest.dto.response;

import com.cinema.service.domain.entity.Movie;
import com.cinema.service.domain.entity.Room;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionListResponse {
    private Long id;
    private LocalDateTime sessionStartTime;
    private LocalDateTime sessionEndTime;
    private BigDecimal basePrice;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Movie movie;
    private Room room;

    public SessionListResponse(Long id, LocalDateTime sessionStartTime, LocalDateTime sessionEndTime, BigDecimal basePrice, Room room) {
        this.id = id;
        this.sessionStartTime = sessionStartTime;
        this.sessionEndTime = sessionEndTime;
        this.basePrice = basePrice;
        this.room = room;
    }
}
