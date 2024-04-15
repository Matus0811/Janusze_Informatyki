package org.project.projectmanagementsystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.control.DeepClone;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.api.dto.ProjectDTO;
import org.project.projectmanagementsystem.database.entities.ProjectEntity;
import org.project.projectmanagementsystem.domain.Project;

@Mapper
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    Project mapFromDtoToDomain(ProjectDTO projectDTO);
    ProjectDTO mapFromDomainToDto(Project project);

    ProjectEntity mapFromDomainToEntity(Project createdProject);
    Project mapFromEntityToDomain(ProjectEntity project);
}
