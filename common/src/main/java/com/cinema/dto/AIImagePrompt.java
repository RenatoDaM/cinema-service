package com.cinema.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class AIImagePrompt {
    @NotBlank
    private String model;
    @NotBlank
    private String prompt;
    @NotBlank
    private String resolution;
    @NotNull
    @Positive
    private Integer n;
}
