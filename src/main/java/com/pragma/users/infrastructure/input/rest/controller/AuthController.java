
package com.pragma.users.infrastructure.input.rest.controller;

import com.pragma.users.application.dto.request.LoginRequestDto;
import com.pragma.users.application.dto.response.LoginResponseDto;
import com.pragma.users.application.port.in.AuthenticateUserUseCase;
import com.pragma.users.infrastructure.output.security.adapter.JwtAuthenticationFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    @Value("${app.security.jwt.expiration-ms}")
    private long expirationMs;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request) {

        String token = authenticateUserUseCase.authenticate(
                request.email(),
                request.password()
        );

        LoginResponseDto response = new LoginResponseDto(
                token,
                JwtAuthenticationFilter.BEARER_PREFIX,
                expirationMs
        );

        return ResponseEntity.ok(response);
    }
}
