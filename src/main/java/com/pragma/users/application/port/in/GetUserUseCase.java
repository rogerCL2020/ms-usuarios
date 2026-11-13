package com.pragma.users.application.port.in;

public interface GetUserUseCase {

    boolean existsByEmail(String email);

}
