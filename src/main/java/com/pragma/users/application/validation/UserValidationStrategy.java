package com.pragma.users.application.validation;

import com.pragma.users.domain.model.User;

public interface UserValidationStrategy {

    boolean supports(String roleName);

    void validate(User user);
}