package com.project.projectmanagementsystem.domain.mapper;

import com.project.projectmanagementsystem.controller.dto.UserDTO;
import com.project.projectmanagementsystem.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToDomainUser(UserDTO userDTO);
    UserDTO mapFromDomainUser(User user);
}
