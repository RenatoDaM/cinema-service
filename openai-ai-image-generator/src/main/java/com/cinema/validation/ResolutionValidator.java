package com.cinema.validation;

import com.cinema.validation.annotation.ValidResolution;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class ResolutionValidator implements ConstraintValidator<ValidResolution, String> {

    private static final List<String> VALID_RESOLUTIONS = Arrays.asList(
            "256x256", "512x512", "1024x1024", "1024x1792", "1792x1024"
    );

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return VALID_RESOLUTIONS.contains(value);
    }
}
