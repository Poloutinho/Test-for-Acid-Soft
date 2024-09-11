package com.example.testtaskforacidsoft.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class YearValidator implements ConstraintValidator<ValidPublicationYear, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int currentYear = Year.now().getValue();
        return value <= currentYear;
    }
}