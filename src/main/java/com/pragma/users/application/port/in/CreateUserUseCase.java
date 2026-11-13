package com.pragma.users.application.port.in;

import com.pragma.users.domain.model.User;

public interface CreateUserUseCase {

    User createUser(User user);

    User createOwner(User user);
 }
 