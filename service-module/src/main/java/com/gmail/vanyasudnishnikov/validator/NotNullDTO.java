package com.gmail.vanyasudnishnikov.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AddItemValidator.class)
public @interface NotNullDTO {
    String message() default "The name must not exceed 40 characters, " +
            "description must not exceed 200 characters, " +
            "status must be in Upper case";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
