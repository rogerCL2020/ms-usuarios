package com.pragma.users.domain.exception;

import com.pragma.users.domain.constant.UserValidationMessages;

public class UnderageUserException extends RuntimeException {
    public UnderageUserException() {
        super(UserValidationMessages.UNDERAGE_USER);
    }
}
