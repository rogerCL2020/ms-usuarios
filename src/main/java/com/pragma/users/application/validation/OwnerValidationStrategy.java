package com.pragma.users.application.validation;

import com.pragma.users.domain.constant.UserValidationMessages;
import com.pragma.users.domain.exception.InvalidUserException;
import com.pragma.users.domain.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

import static com.pragma.users.domain.model.enums.RoleEnum.OWNER_ROLE;

@Component
public class OwnerValidationStrategy implements UserValidationStrategy {

    private static final int MINIMUM_AGE = 18;

    @Override
    public boolean supports(String roleName) {
        return OWNER_ROLE.getRoleName().equalsIgnoreCase(roleName);
    }

    @Override
    public void validate(User user) {

        if (user.getBirthDate() == null) {
            throw new InvalidUserException(
                    UserValidationMessages.BIRTH_DATE_REQUIRED
            );
        }

        int age = Period.between(
                user.getBirthDate(),
                LocalDate.now()
        ).getYears();

        if (age < MINIMUM_AGE) {
            throw new InvalidUserException(
                    UserValidationMessages.OWNER_MUST_BE_ADULT
            );
        }
    }
}