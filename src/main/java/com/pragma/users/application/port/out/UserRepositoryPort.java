package com.pragma.users.application.port.out;

import com.pragma.users.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {

    User save(User user);

    Optional<User> findByEmail(String email);

}
