package com.example.HR.customAnnotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface YearOnly {

    String message() default "Year must be a 4-digit number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
