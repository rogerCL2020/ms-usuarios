package com.pragma.users.application.port.in;

import com.pragma.users.domain.model.User;

public interface GetUserUseCase {

    boolean existsByEmail(String email);

    User getUserById(Long id);

}
