package com.cinema.service.domain.repository;

import com.cinema.service.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
