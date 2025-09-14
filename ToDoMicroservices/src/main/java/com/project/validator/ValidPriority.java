package com.project.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PriorityValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPriority {
    String message() default "Priority must be High or Low";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

