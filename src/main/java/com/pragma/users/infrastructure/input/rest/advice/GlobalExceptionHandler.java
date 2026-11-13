
package com.pragma.users.infrastructure.input.rest.advice;

import com.pragma.users.domain.exception.*;
import com.pragma.users.domain.model.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String MESSAGE = "message";

    private static  final String DEFAULT_ERROR_MESSAGE = "An unexpected error occurred. Please try again later.";

    @ExceptionHandler({UserNotFoundException.class, RoleNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleNotFoundException(RuntimeException exception) {
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({InvalidRoleException.class, InvalidUserException.class})
    public ResponseEntity<Map<String, String>> handleInvalidEntityException(RuntimeException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler({UnderageUserException.class, UserAlreadyExistsException.class})
    public ResponseEntity<Map<String, String>> handleOwnerException(RuntimeException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }




    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(
            AccessDeniedException exception) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        boolean isAnonymous = authentication == null
                || authentication instanceof AnonymousAuthenticationToken
                || !authentication.isAuthenticated();

        if (isAnonymous) {
            return buildResponse(
                    HttpStatus.UNAUTHORIZED,
                    "Debes iniciar sesión para acceder a este recurso"
            );
        }

        return buildResponse(
                HttpStatus.FORBIDDEN,
                "No tienes permisos para realizar esta operación"
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, String>> handleDataAccessException(
            DataAccessException exception) {

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,DEFAULT_ERROR_MESSAGE

        );
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException exception) {

        String errorMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(HttpStatus.BAD_REQUEST, errorMessage);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception) {

        return buildResponse(HttpStatus.BAD_REQUEST, "El cuerpo de la solicitud está vacío o tiene un formato inválido");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
            IllegalArgumentException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<Map<String, String>> buildResponse(
            HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(Collections.singletonMap(MESSAGE, message));
    }

}
