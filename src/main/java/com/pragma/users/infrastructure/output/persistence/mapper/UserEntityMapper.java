
package com.pragma.users.infrastructure.output.persistence.mapper;

import com.pragma.users.domain.model.User;
import com.pragma.users.infrastructure.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {

    UserEntity toEntity(User user);

    @Mapping(target = "roleName", ignore = true)
    User toDomain(UserEntity entity);
}
