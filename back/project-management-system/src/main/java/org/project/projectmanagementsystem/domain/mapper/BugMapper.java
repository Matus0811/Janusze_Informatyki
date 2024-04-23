package org.project.projectmanagementsystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.api.dto.BugFormDTO;
import org.project.projectmanagementsystem.database.entities.BugEntity;
import org.project.projectmanagementsystem.domain.Bug;
import org.project.projectmanagementsystem.domain.BugForm;

@Mapper
public interface BugMapper {
    BugMapper INSTANCE = Mappers.getMapper(BugMapper.class);

    BugForm mapFromDtoToDomain(BugFormDTO bugDTO);
    BugFormDTO mapFromDomainToDto(BugForm bug);

    BugEntity mapFromDomainToEntity(Bug bug);
    Bug mapFromEntityToDomain(BugEntity bugEntity);
}
