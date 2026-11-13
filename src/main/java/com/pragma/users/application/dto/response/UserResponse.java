
package com.pragma.users.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record UserResponse(

        Long id,

        @JsonProperty("nombre")
        String firstName,

        @JsonProperty("apellido")
        String lastName,

        @JsonProperty("numeroDocumento")
        String documentNumber,

        @JsonProperty("celular")
        String phone,

        @JsonProperty("fechaNacimiento")
        LocalDate birthDate,

        @JsonProperty("correo")
        String email,

        @JsonProperty("idRol")
        Long roleId,

        @JsonProperty("nombreRol")
        String roleName
) {}
