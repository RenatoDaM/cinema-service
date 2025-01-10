package com.cinema.dto;

import com.cinema.enums.DALLEModelEnum;
import com.cinema.validation.annotation.ValidDALLEPrompt;
import com.cinema.validation.annotation.ValidResolution;
import com.cinema.validation.annotation.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@ValidDALLEPrompt
public class DALLERequest {
    @ValueOfEnum(enumClass = DALLEModelEnum.class)
    private String model;
    private String prompt;
    private Integer n;
    @ValidResolution
    private String size;
}
