package com.pragma.users.infrastructure.output.persistence.adapter;

import com.pragma.users.application.port.out.RoleRepositoryPort;
import com.pragma.users.domain.model.Role;
import com.pragma.users.infrastructure.output.persistence.entity.RoleEntity;
import com.pragma.users.infrastructure.output.persistence.repository.RoleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleJpaAdapter implements RoleRepositoryPort {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public Optional<Role> findById(Long id) {
        return roleJpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return roleJpaRepository.findByName(name)
                .map(this::toDomain);
    }

    private Role toDomain(
            RoleEntity entity) {

        return new Role(
                entity.getId(),
                entity.getName()
        );
    }
}
