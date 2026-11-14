package com.pragma.users.domain.exception;

import com.pragma.users.domain.constant.UserValidationMessages;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super(UserValidationMessages.USER_ALREADY_EXISTS);
    }
}
