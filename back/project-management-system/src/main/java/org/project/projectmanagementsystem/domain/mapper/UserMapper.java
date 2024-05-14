package org.project.projectmanagementsystem.domain.mapper;

import org.project.projectmanagementsystem.api.dto.UserDTO;
import org.project.projectmanagementsystem.api.dto.UserFormDTO;
import org.project.projectmanagementsystem.api.dto.UserDataDTO;
import org.project.projectmanagementsystem.database.entities.UserEntity;
import org.project.projectmanagementsystem.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.domain.UserData;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapFromDtoToDomain(UserFormDTO userDTO);
    UserFormDTO mapFromDomainToFormDto(User user);

    UserDTO mapFromDomainToDto(User user);
    User mapFromDtoToDomain(UserDTO userDTO);

    User mapFromEntityToDomain(UserEntity user);

    UserEntity mapFromDomainToEntity(User user);

    UserData mapFromDtoToDomain(UserDataDTO userDataDTO);
    UserDataDTO mapFromDomainToDto(UserData userData);
    UserDataDTO mapFromDomainToUserDataDto(User user);

}
