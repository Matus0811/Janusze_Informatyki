package org.project.projectmanagementsystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.database.entities.UserProjectRoleEntity;
import org.project.projectmanagementsystem.domain.UserProjectRole;

@Mapper
public interface UserProjectRoleMapper {
    UserProjectRoleMapper INSTANCE = Mappers.getMapper(UserProjectRoleMapper.class);

    UserProjectRole mapFromEntityToDomain(UserProjectRoleEntity userProjectRoleEntity);
    UserProjectRoleEntity mapFromDomainToEntity(UserProjectRole userProjectRole);

}
