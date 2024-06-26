package com.cinema.service.domain.service;

import com.cinema.service.domain.entity.Room;
import com.cinema.service.domain.repository.RoomRepository;
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
