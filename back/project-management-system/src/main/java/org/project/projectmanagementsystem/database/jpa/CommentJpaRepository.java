package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.CommentEntity;
import org.project.projectmanagementsystem.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CommentJpaRepository extends JpaRepository<CommentEntity,Long> {
    @Query("""
    SELECT ce FROM CommentEntity ce
    JOIN FETCH ce.task t
    JOIN FETCH ce.user u
    WHERE t.taskId = :taskId
    """)
    Page<CommentEntity> findPagedCommentsForTask(@Param("taskId") Long taskId, Pageable pageable);

    @Query("""
    SELECT ce FROM CommentEntity ce
    WHERE ce.user.email = :email
    AND ce.task.project.projectId = :projectId
    """)
    List<CommentEntity> findAllUserCommentsInProject(@Param("email") String userEmail, @Param("projectId")UUID projectId);

    @Query("""
    SELECT ce FROM CommentEntity ce
    JOIN FETCH ce.user u
    JOIN FETCH ce.task t
    WHERE u.username = :username
    AND t.taskCode = :taskCode
    """)
    List<CommentEntity> findAllUserCommentsInTask(@Param("username") String username, @Param("taskCode") String taskCode);
}
