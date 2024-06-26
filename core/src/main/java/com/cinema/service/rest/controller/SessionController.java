package com.cinema.service.rest.controller;

import com.cinema.service.domain.entity.Session;
import com.cinema.service.domain.service.SeatService;
import com.cinema.service.rest.dto.request.SessionCreateRequest;
import com.cinema.service.domain.service.SessionService;
import com.cinema.service.rest.dto.response.SessionListResponse;
import com.cinema.service.rest.dto.response.SessionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sessions")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "${services.bff}")
public class SessionController {
    private final SessionService sessionService;
    private final SeatService seatService;

    @PostMapping("/movies/{movieId}/rooms/{roomId}")
    public ResponseEntity<Session> createSession(
            @Valid @RequestBody SessionCreateRequest sessionCreateRequest,
            @PathVariable Long movieId,
            @PathVariable Long roomId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(sessionService.createSession(sessionCreateRequest, movieId, roomId));
    }
    

    @GetMapping
    public ResponseEntity<Page<SessionListResponse>> getAllSessionsPaginated(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sessionService.findAllSessionsPaginated(page, size));
    }

    @GetMapping("/movies/{movieId}")
    public ResponseEntity<Page<SessionListResponse>> getAllSessionsPaginatedByMovieId(
            @PathVariable Long movieId,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sessionService.findAllSessionsPaginatedByMovieId(movieId, page, size));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable Long sessionId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(sessionService.findById(sessionId));
    }

    @PatchMapping("/seats/{seatId}/mark-unavailable")
    public ResponseEntity<Void> updateSeatToUnavailable(@PathVariable Long seatId) {
        seatService.updateSeatToUnavailable(seatId);
        return ResponseEntity.ok().build();
    }
}
