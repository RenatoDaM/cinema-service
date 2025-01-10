package com.cinema.validation.annotation;

import com.cinema.validation.DALLEPromptValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DALLEPromptValidator.class)
public @interface ValidDALLEPrompt {
    String message() default "in openapi provider, only dall-e-2 model can retrieve more than one image per request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
