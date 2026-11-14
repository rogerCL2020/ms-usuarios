package com.pragma.users.domain.exception;

import com.pragma.users.domain.constant.UserValidationMessages;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super(UserValidationMessages.USER_NOT_FOUND);
    }
}
