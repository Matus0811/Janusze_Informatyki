package org.project.projectmanagementsystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.api.dto.AssignFormDTO;
import org.project.projectmanagementsystem.api.dto.TaskDTO;
import org.project.projectmanagementsystem.api.dto.TaskFormDTO;
import org.project.projectmanagementsystem.database.entities.TaskEntity;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.AssignForm;
import org.project.projectmanagementsystem.domain.TaskForm;

@Mapper
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskForm mapFromDtoToDomain(TaskFormDTO taskFormDTO);

    TaskDTO mapFromDomainToDto(Task task);

    TaskEntity mapFromDomainToEntity(Task task);

    Task mapFromEntityToDomain(TaskEntity task);


}
