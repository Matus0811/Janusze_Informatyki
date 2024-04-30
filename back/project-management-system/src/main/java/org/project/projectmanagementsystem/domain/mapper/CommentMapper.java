package org.project.projectmanagementsystem.domain.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project.projectmanagementsystem.api.dto.CommentFormDTO;
import org.project.projectmanagementsystem.database.entities.CommentEntity;
import org.project.projectmanagementsystem.domain.Comment;
import org.project.projectmanagementsystem.domain.CommentForm;

@Mapper
public interface CommentMapper {
    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);


    CommentForm mapFromDtoToDomain(CommentFormDTO commentFormDTO);

    CommentEntity mapFromDomainToEntity(Comment comment);
    Comment mapFromEntityToDomain(CommentEntity commentEntity);
}
