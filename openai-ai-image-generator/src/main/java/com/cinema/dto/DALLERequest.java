package com.cinema.dto;

import com.cinema.validation.annotation.ValidResolution;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DALLERequest {
    private String model;
    private String prompt;
    private Integer n;
    @ValidResolution
    private String size;
}
