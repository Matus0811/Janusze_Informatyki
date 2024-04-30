package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.jpa.CommentJpaRepository;
import org.project.projectmanagementsystem.domain.Comment;
import org.project.projectmanagementsystem.domain.mapper.CommentMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public void deleteCommentWhereId(Long commentId) {
        commentJpaRepository.deleteByCommentId(commentId);
    }
}
