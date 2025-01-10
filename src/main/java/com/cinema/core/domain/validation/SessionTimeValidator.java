package com.cinema.core.domain.validation;

import com.cinema.core.domain.validation.annotation.ValidSessionTime;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDateTime;

public class SessionTimeValidator implements ConstraintValidator<ValidSessionTime, Object> {
    private String startTimeField;
    private String endTimeField;

    @Override
    public void initialize(ValidSessionTime constraintAnnotation) {
        this.startTimeField = constraintAnnotation.startTimeField();
        this.endTimeField = constraintAnnotation.endTimeField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        LocalDateTime startTime = (LocalDateTime) new BeanWrapperImpl(value)
                .getPropertyValue(startTimeField);
        LocalDateTime endTime = (LocalDateTime) new BeanWrapperImpl(value)
                .getPropertyValue(endTimeField);

        if (startTime != null && endTime != null) {
            return endTime.isAfter(startTime);
        }
        return true;
    }
}