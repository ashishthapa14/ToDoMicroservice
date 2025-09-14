package com.project.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriorityValidator implements ConstraintValidator<ValidPriority, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && (value.equalsIgnoreCase("High") || value.equalsIgnoreCase("Low"));
    }
}

