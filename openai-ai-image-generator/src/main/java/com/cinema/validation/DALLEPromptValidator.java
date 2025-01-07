package com.cinema.validation;

import com.cinema.dto.DALLERequest;
import com.cinema.validation.annotation.ValidDALLEPrompt;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DALLEPromptValidator implements ConstraintValidator<ValidDALLEPrompt, DALLERequest> {

    @Override
    public boolean isValid(DALLERequest value, ConstraintValidatorContext context) {
        String oneImageOnlyModel = "dall-e-3";
        if (oneImageOnlyModel.equals(value.getModel()) && value.getN() > 1) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("dall-e-3 supports only one image per request. try dall-e-2")
                    .addPropertyNode("model")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
