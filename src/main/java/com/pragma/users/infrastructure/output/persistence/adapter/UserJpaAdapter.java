package com.pragma.users.infrastructure.output.persistence.adapter;

import com.pragma.users.application.port.out.UserRepositoryPort;
import com.pragma.users.domain.model.User;
import com.pragma.users.infrastructure.output.persistence.entity.RoleEntity;
import com.pragma.users.infrastructure.output.persistence.entity.UserEntity;
import com.pragma.users.infrastructure.output.persistence.mapper.UserEntityMapper;
import com.pragma.users.infrastructure.output.persistence.repository.RoleJpaRepository;
import com.pragma.users.infrastructure.output.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public User save(User user) {

        UserEntity userEntity = userEntityMapper.toEntity(user);

        UserEntity savedEntity = userJpaRepository.save(userEntity);

        return toDomainWithRole(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {

        return userJpaRepository.findByEmail(email)
                .map(this::toDomainWithRole);
    }

    @Override
    public Optional<User> findById(Long id) {

        return userJpaRepository.findById(id)
                .map(this::toDomainWithRole);
    }


    private User toDomainWithRole(UserEntity entity) {

        User user = userEntityMapper.toDomain(entity);

        roleJpaRepository.findById(entity.getRoleId())
                .map(RoleEntity::getName)
                .ifPresent(user::setRoleName);

        return user;
    }

}
