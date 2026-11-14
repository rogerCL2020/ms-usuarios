package com.pragma.users.domain.exception;

import com.pragma.users.domain.constant.RoleValidationMessages;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException() {
        super(RoleValidationMessages.ROLE_NOT_FOUND);
    }
}
