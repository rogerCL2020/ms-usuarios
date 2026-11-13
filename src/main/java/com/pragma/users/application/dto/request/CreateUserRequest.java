package com.pragma.users.application.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateUserRequest(

        @JsonProperty("nombre")
        @NotBlank
        @Size(max = 100)
        String firstName,

        @JsonProperty("apellido")
        @NotBlank
        @Size(max = 100)
        String lastName,

        @JsonProperty("numeroDocumento")
        @NotBlank
        @Pattern(
                regexp = "\\d+",
                message = "El documento debe contener solo números"
        )
        String documentNumber,

        @JsonProperty("celular")
        @NotBlank(message = "El celular es obligatorio")
        @Pattern(
                regexp = "^\\+?[0-9]{1,12}$",
                message = "El celular debe tener como máximo 13 caracteres, incluido el signo +"
        )
        String phone,

        @JsonProperty("fechaNacimiento")
        @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
        LocalDate birthDate,

        @JsonProperty("correo")
        @NotBlank
        @Email
        @Size(max = 150)
        String email,

        @JsonProperty("clave")
        @NotBlank
        @Size(min = 8, max = 72)
        String password,

        @JsonProperty("idRol")
        @Positive(message = "El idRol debe ser un número positivo")
        Long roleId
) {}