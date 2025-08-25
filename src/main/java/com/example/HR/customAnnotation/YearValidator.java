package com.example.HR.customAnnotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearValidator implements ConstraintValidator<YearOnly,Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext constraintValidatorContext) {
        if(value == null){
            return true;
        }
        return value >=1000 && value<=9999;
    }
}
