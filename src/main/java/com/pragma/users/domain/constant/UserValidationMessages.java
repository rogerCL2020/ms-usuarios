package com.pragma.users.domain.constant;

public final class UserValidationMessages {

    private UserValidationMessages() {}

    public static final String BIRTH_DATE_REQUIRED = "Birth date is required for the owner";

    public static final String OWNER_MUST_BE_ADULT = "The owner must be at least 18 years old";

    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String UNDERAGE_USER = "Underage user";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String EMAIL_ALREADY_EXISTS  = "Email already exists";

}