package org.project.projectmanagementsystem.database.mapper;

import org.project.projectmanagementsystem.database.entities.UserEntity;
import org.project.projectmanagementsystem.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserEntityMapper {

    UserEntityMapper INSTANCE = Mappers.getMapper(UserEntityMapper.class);

    UserEntity mapToEntity(User user);

    User mapFromEntity(UserEntity userEntity);

}
