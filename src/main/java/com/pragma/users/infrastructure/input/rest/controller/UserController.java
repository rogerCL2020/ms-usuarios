package com.pragma.users.infrastructure.input.rest.controller;


import com.pragma.users.application.dto.request.CreateUserRequest;
import com.pragma.users.application.dto.response.UserResponse;
import com.pragma.users.application.port.in.CreateUserUseCase;
import com.pragma.users.application.port.in.DeleteUserUseCase;
import com.pragma.users.application.port.in.GetUserUseCase;
import com.pragma.users.domain.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;
    private final GetUserUseCase getUserUseCase;
    private final DeleteUserUseCase deleteEmployeeUseCase;


    @PostMapping("/owner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> createOwner(@Valid @RequestBody CreateUserRequest request) {

        User user = toUser(request);

        User createdUser = createUserUseCase.createOwner(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(createdUser));
    }

    @PostMapping("/employee")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROPIETARIO')")
    public ResponseEntity<UserResponse> createEmployee(
            @Valid @RequestBody CreateUserRequest request) {

        User user = toUser(request);

        User createdUser = createUserUseCase.createEmployee(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(createdUser));
    }

    @PostMapping("/client")
    public ResponseEntity<UserResponse> createClient(
            @Valid @RequestBody CreateUserRequest request) {

        User user = toUser(request);

        User createdUser = createUserUseCase.createClient(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toResponse(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        User user = getUserUseCase.getUserById(id);

        return ResponseEntity.ok(toResponse(user));
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByEmail(
            @RequestParam("correo") String email) {

        return ResponseEntity.ok(getUserUseCase.existsByEmail(email));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROPIETARIO')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        deleteEmployeeUseCase.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    private User toUser(CreateUserRequest request) {
        return new User(
                null,
                request.firstName(),
                request.lastName(),
                request.documentNumber(),
                request.phone(),
                request.birthDate(),
                request.email(),
                request.password(),
                null,
                null
        );
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getDocumentNumber(),
                user.getPhone(),
                user.getBirthDate(),
                user.getEmail(),
                user.getRoleId(),
                user.getRoleName()
        );
    }
}
