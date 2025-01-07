package com.cinema.enums;

import com.cinema.interfaces.EnumValue;

public enum DALLEModelEnum implements EnumValue {
    DALL_E_2("dall-e-2"),
    DALL_E_3("dall-e-3");

    private final String modelName;

    DALLEModelEnum(String modelName) {
        this.modelName = modelName;
    }

    public String getValue() {
        return modelName;
    }
}