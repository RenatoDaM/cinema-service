package com.cinema.core.service;

import com.cinema.core.domain.entity.Movie;
import com.cinema.core.domain.entity.Room;
import com.cinema.core.domain.entity.Seat;
import com.cinema.core.domain.entity.Session;
import com.cinema.core.common.enums.SeatTypeEnum;
import com.cinema.core.domain.mapper.MovieMapper;
import com.cinema.dataproviders.database.repository.MovieRepository;
import com.cinema.dataproviders.database.repository.SessionRepository;
import com.cinema.entrypoints.api.dto.request.SessionCreateRequest;
import com.cinema.entrypoints.api.dto.response.SeatResponse;
import com.cinema.entrypoints.api.dto.response.SessionListResponse;
import com.cinema.entrypoints.api.dto.response.SessionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final RoomService roomService;
    private final MovieRepository movieRepository;

    @Value("${cinema.max-session-hours}")
    private Integer maxSessionHours;
    @Value("${cinema.min-session-minutes}")
    private Integer minimumSessionMinutes;

    public Session createSession(SessionCreateRequest session, Long movieId, Long roomId) {
        Movie movieEntity = movieRepository.findById(movieId).orElseThrow(() ->
                new IllegalArgumentException("Movie with id: " + movieId + " not found"));

        Room room = roomService.findById(roomId);
        List<Seat> seats = createSeatsForSession(room.getTotalSeats());

        validateRoomAvailability(session.getSessionStartTime(), session.getSessionEndTime(), roomId);
        validateSessionDuration(session);

        return sessionRepository.save(
                new Session(
                        null,
                        session.getSessionStartTime(),
                        session.getSessionEndTime(),
                        session.getBasePrice(),
                        movieEntity,
                        room,
                        seats
                ));
    }
    

    public Page<SessionListResponse> findAllSessionsPaginated(int page, int size) {
        return sessionRepository.findAllSessionsPaginated(PageRequest.of(page, size));
    }

    public Page<SessionListResponse> findAllSessionsPaginatedByMovieId(Long movieId, int page, int size) {
        return sessionRepository.findAllSessionsPaginatedByMovieId(movieId, PageRequest.of(page, size));
    }

    public SessionResponse findById(Long sessionId) {
        Session sessionEntity = sessionRepository.findByIdOrderBySeatNumber(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("session with id: [" + sessionId + "] not found"));
        return new SessionResponse(
                sessionEntity.getId(),
                sessionEntity.getSessionStartTime(),
                sessionEntity.getSessionEndTime(),
                sessionEntity.getBasePrice(),
                MovieMapper.INSTANCE.toDto(sessionEntity.getMovie()),
                sessionEntity.getRoom(),
                sessionEntity.getSeats().stream().map(seat -> new SeatResponse(
                        seat.getId(),
                        seat.getSeatNumber(),
                        seat.getAvailable(),
                        seat.getType()
                        )
                ).toList()
        );
    }

    private void validateRoomAvailability(
            LocalDateTime sessionStartTime,
            LocalDateTime sessionEndTime,
            Long roomId) {

        Boolean roomIsEmptyAtThisTime = sessionRepository.isRoomEmptyAtThisTime(sessionStartTime, sessionEndTime, roomId);
        if (!roomIsEmptyAtThisTime)
            throw new IllegalArgumentException("this room already have a session at this time period");
    }

    private void validateSessionDuration(SessionCreateRequest session) {
        LocalDateTime sessionEndTime = session.getSessionEndTime();
        LocalDateTime sessionStartTimePlusLimit = session.getSessionStartTime().plusHours(maxSessionHours);

        Boolean exceedsSessionTimeLimit = sessionEndTime.isAfter(sessionStartTimePlusLimit)
                || sessionEndTime.isEqual(sessionStartTimePlusLimit);

        if (exceedsSessionTimeLimit) {
            throw new IllegalArgumentException("session has exceeded the time limit. if this is intentional and not an error, please contact the administrator");
        }

        LocalDateTime sessionStartTimePlusMinimumAllowed = session.getSessionStartTime().plusMinutes(minimumSessionMinutes);

        Boolean isSessionTimeBelowMinimum = sessionEndTime.isBefore(sessionStartTimePlusMinimumAllowed);
        if (isSessionTimeBelowMinimum) {
            throw new IllegalArgumentException("session duration is less than the minimum required. please ensure the session duration is at least 10 minutes");
        }
    }

    private List<Seat> createSeatsForSession(Integer totalSeats) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= totalSeats; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(i);
            seat.setAvailable(true);
            seat.setType(SeatTypeEnum.STANDARD);
            seats.add(seat);
        }
        return seats;
    }
}
