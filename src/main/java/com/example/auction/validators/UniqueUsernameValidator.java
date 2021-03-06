package com.example.auction.validators;

import com.example.auction.models.User;
import com.example.auction.services.UserService;
import com.example.auction.validators.annotations.UniqueUsername;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, User> {

    @Autowired
    private UserService userService;

    public void initialize(UniqueUsername constraint) {
    }

    public boolean isValid(User user, ConstraintValidatorContext context) {
        return userService == null || (user != null && userService.isUniqueLogin(user));
    }

}