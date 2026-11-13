package com.pragma.users.domain.model.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    ADMIN_ROLE("ADMIN"),
    OWNER_ROLE("PROPIETARIO"),
    EMPLOYEE_ROLE("EMPLEADO"),
    CLIENT_ROLE("CLIENTE");

    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }


}
