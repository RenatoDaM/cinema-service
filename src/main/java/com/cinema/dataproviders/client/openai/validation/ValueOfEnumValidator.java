package com.cinema.dataproviders.client.openai.validation;

import com.cinema.core.common.interfaces.EnumValue;
import com.cinema.dataproviders.client.openai.validation.annotation.ValueOfEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
    private List<String> acceptedValues;
    private String acceptedValuesMessage;

    @Override
    public void initialize(ValueOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .filter(enumConstant -> enumConstant instanceof EnumValue)
                .map(enumConstant -> ((EnumValue) enumConstant).getValue())
                .collect(Collectors.toList());
        acceptedValuesMessage = String.join(", ", acceptedValues);
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        boolean isValid = acceptedValues.contains(value.toString());
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "must be any of the values: " + acceptedValuesMessage
            ).addConstraintViolation();
        }
        return isValid;
    }
}
