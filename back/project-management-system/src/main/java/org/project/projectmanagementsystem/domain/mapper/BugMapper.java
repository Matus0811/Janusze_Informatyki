package org.project.projectmanagementsystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.api.dto.BugDTO;
import org.project.projectmanagementsystem.api.dto.BugFormDTO;
import org.project.projectmanagementsystem.database.entities.BugEntity;
import org.project.projectmanagementsystem.domain.Bug;
import org.project.projectmanagementsystem.domain.BugForm;

@Mapper
public interface BugMapper {
    BugMapper INSTANCE = Mappers.getMapper(BugMapper.class);

    BugForm mapFromDtoToDomain(BugFormDTO bugDTO);
    BugFormDTO mapFromFormToDto(BugForm bug);

    @Mappings({
            @Mapping(source = "bug.projectWithBug", target = "project"),
            @Mapping(source = "bug.taskWithBug", target = "taskEntity"),
            @Mapping(source = "bug.reportedUser", target = "user")
    })
    BugEntity mapFromDomainToEntity(Bug bug);

    @Mappings({
            @Mapping(source = "bugEntity.project", target = "projectWithBug"),
            @Mapping(source = "bugEntity.taskEntity", target = "taskWithBug"),
            @Mapping(source = "bugEntity.user", target = "reportedUser"),
    })
    Bug mapFromEntityToDomain(BugEntity bugEntity);

    @Mappings({
            @Mapping(source = "bug.taskWithBug", target = "task"),
            @Mapping(source = "bug.reportedUser.username", target = "username")
    })
    BugDTO mapFromDomainToDto(Bug bug);
}
