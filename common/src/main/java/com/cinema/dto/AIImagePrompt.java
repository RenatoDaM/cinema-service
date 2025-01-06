package com.cinema.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private Integer n;
}
