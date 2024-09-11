package com.example.testtaskforacidsoft.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = YearValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPublicationYear {

    String message() default "Publication year must not be in the future";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
