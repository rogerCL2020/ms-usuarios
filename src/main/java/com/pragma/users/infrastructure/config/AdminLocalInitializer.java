
package com.pragma.users.infrastructure.config;

import com.pragma.users.application.port.in.CreateUserUseCase;
import com.pragma.users.application.port.out.RoleRepositoryPort;
import com.pragma.users.application.port.out.UserRepositoryPort;
import com.pragma.users.domain.exception.RoleNotFoundException;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;

import com.pragma.users.domain.model.enums.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
@RequiredArgsConstructor
public class AdminLocalInitializer {


    public static final String ADMIN_FIRST_NAME = "Administrador";
    public static final String ADMIN_LAST_NAME = "Local";
    public static final String ADMIN_DOCUMENT = "99999999";
    public static final String ADMIN_PHONE = "+51999999999";

    public static final String ADMIN_ROLE_NOT_FOUND = "ADMIN role does not exist";

    private final CreateUserUseCase createUserUseCase;
    private final UserRepositoryPort userRepositoryPort;
    private final RoleRepositoryPort roleRepositoryPort;

    @Bean
    CommandLineRunner initializeLocalAdmin(
            @Value("${app.bootstrap-admin.email}") String email,
            @Value("${app.bootstrap-admin.password}") String password) {

        return args -> {

            if (userRepositoryPort.findByEmail(email).isPresent()) {
                return;
            }

            Role adminRole = roleRepositoryPort.findByName(RoleEnum.ADMIN_ROLE.getRoleName())
                    .orElseThrow(RoleNotFoundException::new);

            User admin = new User(
                    null,
                    ADMIN_FIRST_NAME,
                    ADMIN_LAST_NAME,
                    ADMIN_DOCUMENT,
                    ADMIN_PHONE,
                    null,
                    email,
                    password,
                    adminRole.getId(),
                    adminRole.getName()
            );

            createUserUseCase.createUser(admin);
        };
    }
}
