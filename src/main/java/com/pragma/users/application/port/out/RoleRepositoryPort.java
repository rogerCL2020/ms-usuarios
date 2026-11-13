package com.pragma.users.application.port.out;

import com.pragma.users.domain.model.Role;

import java.util.Optional;

public interface RoleRepositoryPort {

    Optional<Role> findById(Long id);

    Optional<Role> findByName(String name);
}
