package com.cinema.entrypoints.api.dto.request;

import com.cinema.core.domain.validation.annotation.ValidSessionTime;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ValidSessionTime(startTimeField = "sessionStartTime", endTimeField = "sessionEndTime", message = "session end time must be after session start time")
public class SessionCreateRequest {
    @Future(message = "it's not possible to create a session in the past")
    @NotNull(message = "sessionStartTime required")
    private LocalDateTime sessionStartTime;
    @Future(message = "it's not possible to create a session ending in the past")
    @NotNull(message = "sessionEnd time required")
    private LocalDateTime sessionEndTime;
    @NotNull(message = "basePrice required")
    @Positive(message = "session base price cannot be zero or negative")
    private BigDecimal basePrice;
}
