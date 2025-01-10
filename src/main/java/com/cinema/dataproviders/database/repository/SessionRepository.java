package com.cinema.dataproviders.database.repository;

import com.cinema.core.domain.entity.Session;
import com.cinema.entrypoints.api.dto.response.SessionListResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("SELECT s FROM Session s JOIN FETCH s.seats st WHERE s.id = :sessionId ORDER BY st.seatNumber")
    Optional<Session> findByIdOrderBySeatNumber(@Param("sessionId") Long sessionId);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN FALSE ELSE TRUE END " +
            "FROM Session s " +
            "WHERE s.room.id = :roomId " +
            "AND :sessionTime <= s.sessionStartTime " +
            "AND :sessionEndTime >= s.sessionStartTime")
    Boolean isRoomEmptyAtThisTime(@Param("sessionTime") LocalDateTime sessionStartTime,
                                  @Param("sessionEndTime") LocalDateTime sessionEndTime,
                                  @Param("roomId") Long roomId);

    @Query("SELECT new com.cinema.entrypoints.api.dto.response.SessionListResponse(s.id, s.sessionStartTime, s.sessionEndTime, s.basePrice, s.movie, s.room) " +
            "FROM Session s " +
            "JOIN s.movie m " +
            "JOIN s.room r")
    Page<SessionListResponse> findAllSessionsPaginated(Pageable pageable);

    @Query("SELECT new com.cinema.entrypoints.api.dto.response.SessionListResponse(s.id, s.sessionStartTime, s.sessionEndTime, s.basePrice, s.room) " +
            "FROM Session s " +
            "JOIN s.movie m " +
            "JOIN s.room r WHERE s.movie.id = :movieId")
    Page<SessionListResponse> findAllSessionsPaginatedByMovieId(@Param("movieId") Long movieId, Pageable pageable);
}
