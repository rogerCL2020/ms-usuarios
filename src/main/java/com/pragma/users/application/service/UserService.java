
package com.pragma.users.application.service;

import com.pragma.users.application.port.in.CreateUserUseCase;
import com.pragma.users.application.port.in.GetUserUseCase;
import com.pragma.users.application.port.out.PasswordEncoderPort;
import com.pragma.users.application.port.out.RoleRepositoryPort;
import com.pragma.users.application.port.out.UserRepositoryPort;
import com.pragma.users.application.validation.UserValidationStrategy;
import com.pragma.users.domain.exception.InvalidRoleException;
import com.pragma.users.domain.exception.InvalidUserException;
import com.pragma.users.domain.exception.UserNotFoundException;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pragma.users.domain.constant.RoleValidationMessages.ROLE_ID_REQUIRED;
import static com.pragma.users.domain.constant.RoleValidationMessages.ROLE_NOT_FOUND;
import static com.pragma.users.domain.constant.UserValidationMessages.EMAIL_ALREADY_EXISTS;
import static com.pragma.users.domain.model.enums.RoleEnum.OWNER_ROLE;

@Service
@RequiredArgsConstructor
public class UserService implements CreateUserUseCase, GetUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final List<UserValidationStrategy> validationStrategies;

    @Override
    @Transactional
    public User createUser(User user) {
        Role role = findRoleById(user.getRoleId());
        return registerUser(user, role);
    }

    @Override
    @Transactional
    public User createOwner(User user) {
        Role role = findRoleByName(OWNER_ROLE.getRoleName());
        return registerUser(user, role);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepositoryPort.findByEmail(email).isPresent();
    }


    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepositoryPort.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }


    private Role findRoleById(Long roleId) {

        if (roleId == null) {
            throw new InvalidRoleException(ROLE_ID_REQUIRED);
        }

        return roleRepositoryPort.findById(roleId)
                .orElseThrow(() ->
                        new InvalidRoleException(ROLE_NOT_FOUND));
    }

    private Role findRoleByName(String roleName) {
        return roleRepositoryPort.findByName(roleName)
                .orElseThrow(() ->
                        new InvalidRoleException(ROLE_NOT_FOUND));
    }

    private User registerUser(User user, Role role) {

        if (existsByEmail(user.getEmail())) {
            throw new InvalidUserException(EMAIL_ALREADY_EXISTS);
        }

        user.setRoleId(role.getId());
        user.setRoleName(role.getName());

        validateUser(user, role);

        user.setPassword(
                passwordEncoderPort.encode(user.getPassword())
        );

        return userRepositoryPort.save(user);
    }

    private void validateUser(User user, Role role) {
        validationStrategies.stream()
                .filter(strategy -> strategy.supports(role.getName()))
                .forEach(strategy -> strategy.validate(user));
    }
}
