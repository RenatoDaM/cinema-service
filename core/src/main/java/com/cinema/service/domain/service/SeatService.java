package com.cinema.service.domain.service;

import com.cinema.service.domain.entity.Seat;
import com.cinema.service.domain.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private static final Logger log = LoggerFactory.getLogger(SeatService.class);

    public Seat findById(Long id) {
        return seatRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("seat with id " + id + " not found"));
    }

    public void updateSeat(Seat seat) {
        log.info("updating seat [{}]", seat);
        seatRepository.save(seat);
    }

    public void updateSeatToUnavailable(Long seatId) {
        var seat = findById(seatId);

        if (!seat.getAvailable())
            throw new IllegalArgumentException("seat with id " + seatId + " is already occupied");

        seat.setAvailable(false);
        log.info("updating seat to Unavailable [{}]", seat);
        seatRepository.save(seat);
    }
}
