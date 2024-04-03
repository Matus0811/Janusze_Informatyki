package org.project.projectmanagementsystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.api.dto.ProjectFormDTO;
import org.project.projectmanagementsystem.domain.ProjectForm;

@Mapper
public interface ProjectFormMapper {
    ProjectFormMapper INSTANCE = Mappers.getMapper(ProjectFormMapper.class);

    ProjectForm mapToDomain(ProjectFormDTO projectFormDTO);
}
