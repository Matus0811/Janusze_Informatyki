package org.project.projectmanagementsystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.api.dto.UserTaskDTO;
import org.project.projectmanagementsystem.database.entities.UserTaskEntity;
import org.project.projectmanagementsystem.domain.UserTask;

@Mapper
public interface UserTaskMapper {
    UserTaskMapper INSTANCE = Mappers.getMapper(UserTaskMapper.class);


    UserTaskEntity mapFromDomainToEntity(UserTask userTask);

    UserTask mapFromEntityToDomain(UserTaskEntity userTaskEntity);

    UserTaskDTO mapFromDomainToDto(UserTask userTask);
}
