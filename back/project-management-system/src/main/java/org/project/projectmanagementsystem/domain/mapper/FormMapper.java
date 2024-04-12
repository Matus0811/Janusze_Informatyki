package org.project.projectmanagementsystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.api.dto.AssignFormDTO;
import org.project.projectmanagementsystem.api.dto.ProjectFormDTO;
import org.project.projectmanagementsystem.domain.AssignForm;
import org.project.projectmanagementsystem.domain.ProjectForm;

@Mapper
public interface FormMapper {
    FormMapper INSTANCE = Mappers.getMapper(FormMapper.class);

    ProjectForm mapFromDtoToDomain(ProjectFormDTO projectFormDTO);
    AssignForm mapFromDtoToDomain(AssignFormDTO assignFormDTO);
}
