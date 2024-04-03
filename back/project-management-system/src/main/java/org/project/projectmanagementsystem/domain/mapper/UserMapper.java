package org.project.projectmanagementsystem.domain.mapper;

import org.project.projectmanagementsystem.api.dto.UserDTO;
import org.project.projectmanagementsystem.database.entities.UserEntity;
import org.project.projectmanagementsystem.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToDomainUser(UserDTO userDTO);
    UserDTO mapFromDomainUser(User user);

    User mapFromEntity(UserEntity user);

    UserEntity mapFromDomain(User user);


}
