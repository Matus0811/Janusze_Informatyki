package com.project.projectmanagementsystem.database.mapper;

import com.project.projectmanagementsystem.database.entities.RoleEntity;
import com.project.projectmanagementsystem.domain.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleEntityMapper {

    RoleEntityMapper INSTANCE = Mappers.getMapper(RoleEntityMapper.class);

    Role mapFromEntity(RoleEntity role);
    RoleEntity mapToEntity(Role role);
}
