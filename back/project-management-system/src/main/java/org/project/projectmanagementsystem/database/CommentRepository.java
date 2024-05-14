package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.entities.CommentEntity;
import org.project.projectmanagementsystem.database.jpa.CommentJpaRepository;
import org.project.projectmanagementsystem.domain.Comment;
import org.project.projectmanagementsystem.domain.mapper.CommentMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final CommentJpaRepository commentJpaRepository;
    public Comment save(Comment newComment) {
        return CommentMapper.INSTANCE.mapFromEntityToDomain(
                commentJpaRepository.save(CommentMapper.INSTANCE.mapFromDomainToEntity(newComment))
        );
    }

    public List<Comment> findPagedCommentsForTask(Long taskId, Pageable pageable) {
        return commentJpaRepository.findPagedCommentsForTask(taskId,pageable)
                .stream()
                .map(CommentMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }

    public void deleteComment(Long commentToDeleteId) {
        commentJpaRepository.deleteById(commentToDeleteId);
    }

    public void removeUserCommentsInProject(List<Comment> commentsToRemove) {
        List<CommentEntity> commentEntities = commentsToRemove.stream()
                .map(CommentMapper.INSTANCE::mapFromDomainToEntity)
                .toList();

        commentJpaRepository.deleteAll(commentEntities);
    }

    public List<Comment> findAllUserCommentsInProject(String userEmail, UUID projectId) {
        return commentJpaRepository.findAllUserCommentsInProject(userEmail,projectId).stream()
                .map(CommentMapper.INSTANCE::mapFromEntityToDomain)
                .toList();
    }
}
