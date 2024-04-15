package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.UserTaskEntity;
import org.project.projectmanagementsystem.domain.UserTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserTaskJpaRepository extends JpaRepository<UserTaskEntity, Long> {

    @Query("""
            SELECT ute FROM UserTaskEntity ute
            JOIN FETCH ute.user u
            JOIN FETCH ute.task t
            WHERE t.taskId = :taskId AND u.userId = :userId
            """)
    Optional<UserTaskEntity> findByTaskIdAndUserId(@Param("taskId") Long taskId, @Param("userId") Long userId);

    @Query("""
    SELECT ute FROM UserTaskEntity ute
    JOIN FETCH ute.user u
    JOIN FETCH ute.task t
    JOIN FETCH t.project p
    WHERE t.taskCode = :taskCode
    AND p.projectId = :projectId
    AND ute.finished != true
    """)
    List<UserTaskEntity> findNotFinishedUserTask(@Param("taskCode") String taskCode,@Param("projectId") UUID projectId);

    @Query("""
    SELECT ute FROM UserTaskEntity ute
    JOIN FETCH ute.user u
    JOIN FETCH ute.task t
    JOIN FETCH t.project p
    WHERE u.userId = :userId
    AND p.projectId = :projectId
    """)
    List<UserTaskEntity> findAllUserTasksAssignedToUserInProject(
            @Param("userId") Long userId,
            @Param("projectId") UUID projectId);
}
