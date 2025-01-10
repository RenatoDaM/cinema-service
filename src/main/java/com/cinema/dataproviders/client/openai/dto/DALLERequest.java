package com.cinema.dataproviders.client.openai.dto;

import com.cinema.dataproviders.client.openai.enums.DALLEModelEnum;
import com.cinema.dataproviders.client.openai.validation.annotation.ValidDALLEPrompt;
import com.cinema.dataproviders.client.openai.validation.annotation.ValidResolution;
import com.cinema.dataproviders.client.openai.validation.annotation.ValueOfEnum;
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
