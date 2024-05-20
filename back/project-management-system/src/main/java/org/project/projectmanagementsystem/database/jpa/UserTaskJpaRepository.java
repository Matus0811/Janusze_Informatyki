package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.TaskEntity;
import org.project.projectmanagementsystem.database.entities.UserTaskEntity;
import org.project.projectmanagementsystem.domain.Task;
import org.project.projectmanagementsystem.domain.UserTask;
import org.springframework.data.domain.Pageable;
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
            WHERE t.taskCode = :taskCode AND u.email = :email
            """)
    Optional<UserTaskEntity> findByTaskCodeAndUserEmail(@Param("taskCode") String taskCode, @Param("email") String email);

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
    WHERE u.email = :email
    AND p.projectId = :projectId
    """)
    List<UserTaskEntity> findAllUserTasksAssignedToUserInProject(
            @Param("email") String userEmail,
            @Param("projectId") UUID projectId);

    @Query("""
    SELECT ute FROM UserTaskEntity ute
    JOIN FETCH ute.user u
    JOIN FETCH ute.task t
    WHERE t.taskCode = :taskCode
    """)
    List<UserTaskEntity> findPagedUsersAssignedToTask(@Param("taskCode") String taskCode, Pageable pageable);

    @Query("""
    SELECT ute FROM UserTaskEntity ute
    JOIN FETCH ute.user u
    JOIN FETCH ute.task t
    WHERE t.taskCode = :taskCode
    """)
    List<UserTaskEntity> findAllUsersAssignedToTask(@Param("taskCode") String taskCode);
}
