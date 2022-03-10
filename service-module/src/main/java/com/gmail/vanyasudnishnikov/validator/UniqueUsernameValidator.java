package com.gmail.vanyasudnishnikov.validator;

import com.gmail.vanyasudnishnikov.UsernameService;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private final UsernameService usernameService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (usernameService.isExists(username)) {
            return false;
        }
        return true;
    }
}
