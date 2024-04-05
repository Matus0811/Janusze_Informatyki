package org.project.projectmanagementsystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.database.entities.RoleEntity;
import org.project.projectmanagementsystem.domain.Role;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role mapFromEntityToDomain(RoleEntity roleEntity);
    RoleEntity mapFromDomainToEntity(Role role);
}
