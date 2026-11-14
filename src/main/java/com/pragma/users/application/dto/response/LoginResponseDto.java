
package com.pragma.users.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponseDto(

        @JsonProperty("token")
        String token,

        @JsonProperty("tipo")
        String type,

        @JsonProperty("expiracionMs")
        long expirationMs

) {}
