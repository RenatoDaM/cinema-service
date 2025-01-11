package com.cinema.dataproviders.database.repository;

import com.cinema.core.domain.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
