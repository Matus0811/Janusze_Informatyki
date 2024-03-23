package org.project.projectmanagementsystem.database.mapper;

import org.project.projectmanagementsystem.database.entities.RoleEntity;
import org.project.projectmanagementsystem.domain.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoleEntityMapper {

    RoleEntityMapper INSTANCE = Mappers.getMapper(RoleEntityMapper.class);

    Role mapFromEntity(RoleEntity role);
    RoleEntity mapToEntity(Role role);
}
