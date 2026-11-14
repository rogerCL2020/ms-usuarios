
package com.pragma.users.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @JsonProperty("correo")
        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato válido")
        String email,

        @JsonProperty("clave")
        @NotBlank(message = "La contraseña es obligatoria")
        String password
) {}