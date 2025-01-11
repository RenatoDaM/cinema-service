package com.cinema.dataproviders.client.openai.validation.annotation;

import com.cinema.dataproviders.client.openai.validation.DALLEPromptValidator;
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

    String message() default "Invalid DALLE request parameters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
