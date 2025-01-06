package com.cinema.validation.annotation;

import com.cinema.validation.ResolutionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ResolutionValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidResolution {

    String message() default "resolution has a invalid value. valid resolutions are ['256x256', '512x512', '1024x1024', '1024x1792', '1792x1024']";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
