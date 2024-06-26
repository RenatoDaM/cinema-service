package com.cinema.service.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MovieCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String synopsis;
    @NotBlank
    private String ageGroup;
    private String category;
    @NotNull
    private LocalDate releaseDate;
    @NotNull
    private LocalTime duration;
    @NotBlank
    private String director;
    @NotBlank
    private String movieCast;
    @NotBlank
    private String producer;
}
