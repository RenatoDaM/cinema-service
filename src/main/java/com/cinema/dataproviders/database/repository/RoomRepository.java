package com.cinema.dataproviders.database.repository;

import com.cinema.core.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
