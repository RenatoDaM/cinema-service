package com.cinema.core.service;

import com.cinema.core.domain.entity.Room;
import com.cinema.dataproviders.database.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Room with id: " + id + " not found"));
    }
}
