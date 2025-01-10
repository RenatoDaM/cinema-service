/*
package com.cinema.service.service;

import com.cinema.core.domain.entity.Movie;
import com.cinema.core.domain.entity.Room;
import com.cinema.dataproviders.database.repository.SessionRepository;
import com.cinema.core.service.MovieService;
import com.cinema.core.service.RoomService;
import com.cinema.core.service.SessionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @InjectMocks
    private SessionService sessionService;
    @Mock
    private MovieService movieService;
    @Mock
    private RoomService roomService;
    @Mock
    private SessionRepository sessionRepository;

    private final Integer maxSessionHours = 5;
    private final Integer minimumSessionMinutes = 30;

    @Test
    public void givenCreatingNewSession_whenSessionTimeConflicts_thenShouldThrow() {
        var movie = buildMovieExample();
        var room = buildRoomExample();

        when(movieService.findById(anyLong()))
                .thenReturn(movie);

        when(roomService.findById(anyLong()))
                .thenReturn(room);

        when(sessionRepository.isRoomEmptyAtThisTime(any(), any(), anyLong()))
                .thenReturn(false);

        SessionCreateRequest session = new SessionCreateRequest(LocalDateTime.now().plusHours(5), LocalDateTime.now().plusHours(4), BigDecimal.TWO);

        assertThrows(IllegalArgumentException.class, () ->
                sessionService.createSession(session, 1L, 1L));
    }

    @Test
    public void givenCreatingNewSession_whenSessionMoreThanMax_thenShouldThrow() {
        var movie = buildMovieExample();
        var room = buildRoomExample();

        ReflectionTestUtils.setField(sessionService, "maxSessionHours", maxSessionHours);
        ReflectionTestUtils.setField(sessionService, "minimumSessionMinutes", minimumSessionMinutes);

        when(movieService.findById(anyLong()))
                .thenReturn(movie);

        when(roomService.findById(anyLong()))
                .thenReturn(room);

        when(sessionRepository.isRoomEmptyAtThisTime(any(), any(), anyLong()))
                .thenReturn(true);

        SessionCreateRequest session = new SessionCreateRequest(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(maxSessionHours + 1), BigDecimal.TWO);

        assertThrows(IllegalArgumentException.class, () ->
                sessionService.createSession(session, 1L, 1L));
    }

    @Test
    public void givenCreatingNewSession_whenValid_thenCreate() {
        var movie = buildMovieExample();
        var room = buildRoomExample();

        ReflectionTestUtils.setField(sessionService, "maxSessionHours", maxSessionHours);
        ReflectionTestUtils.setField(sessionService, "minimumSessionMinutes", minimumSessionMinutes);

        when(movieService.findById(anyLong()))
                .thenReturn(movie);

        when(roomService.findById(anyLong()))
                .thenReturn(room);

        when(sessionRepository.isRoomEmptyAtThisTime(any(), any(), anyLong()))
                .thenReturn(true);

        SessionCreateRequest session = new SessionCreateRequest(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(maxSessionHours-1), BigDecimal.TWO);

        assertDoesNotThrow(() ->
                sessionService.createSession(session, 1L, 1L));
    }

    public Movie buildMovieExample() {
        return new Movie(
                1L,
                "Test Movie",
                "A very adult film",
                "18", "Adult Film",
                LocalDate.now(), "1H30M",
                "Test Director",
                "Actor One, Actor Two",
                "Test Producer"
        );
    }

    public Room buildRoomExample() {
        return new Room(
                1L,
                1,
                40
        );
    }
}
*/
