package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.CommentEntity;
import org.project.projectmanagementsystem.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<CommentEntity,Long> {
    @Query("""
    SELECT ce FROM CommentEntity ce
    JOIN FETCH ce.task t
    WHERE t.taskId = :taskId
    """)
    Page<CommentEntity> findPagedCommentsForTask(@Param("taskId") Long taskId, Pageable pageable);


    void deleteByCommentId(Long commentId);
}
