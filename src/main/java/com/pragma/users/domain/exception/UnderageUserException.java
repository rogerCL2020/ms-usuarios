package com.pragma.users.domain.exception;

import com.pragma.users.domain.constant.UserValidationMessages;

public class UnderageUserException extends RuntimeException {
    public UnderageUserException(String message) {
        super(UserValidationMessages.UNDERAGE_USER);
    }
}
