
package com.pragma.users.application.service;

import com.pragma.users.application.exception.UserAuthenticationException;
import com.pragma.users.application.port.in.AuthenticateUserUseCase;
import com.pragma.users.application.port.out.PasswordEncoderPort;
import com.pragma.users.application.port.out.TokenServicePort;
import com.pragma.users.application.port.out.UserRepositoryPort;
import com.pragma.users.domain.constant.UserValidationMessages;
import com.pragma.users.domain.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthenticateUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenServicePort tokenServicePort;


    @Override
    @Transactional(readOnly = true)
    public String authenticate(String email, String password) {

        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UserAuthenticationException(UserValidationMessages.EMAIL_NOT_EXISTS));

        if (!passwordEncoderPort.matches(password, user.getPassword())) {
            throw new UserAuthenticationException(UserValidationMessages.CREDENTIALS_INVALID);
        }

        return tokenServicePort.generateToken(user.getId(), user.getEmail(), user.getRoleName());
    }

}
