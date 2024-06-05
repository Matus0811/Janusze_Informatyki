package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.api.dto.ProjectTaskStatusCount;
import org.project.projectmanagementsystem.database.entities.TaskEntity;
import org.project.projectmanagementsystem.domain.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface TaskJpaRepository extends JpaRepository<TaskEntity,Long> {
    Optional<TaskEntity> findByTaskCode(String taskCode);

    @Query("""
            SELECT t FROM TaskEntity t
            JOIN FETCH t.project p
            WHERE p.projectId = :projectId
            AND t.status IN :statuses
            """)
    List<TaskEntity> findProjectTasksWithStatus(
            @Param("projectId")UUID projectId,
            @Param("statuses") EnumSet<Task.TaskStatus> taskStatuses, Pageable pageable);

    @Query("""
    SELECT new org.project.projectmanagementsystem.api.dto.ProjectTaskStatusCount(t.status,count(*)) FROM TaskEntity t
    WHERE t.project.projectId = :projectId
    GROUP BY t.status
    """)
    List<ProjectTaskStatusCount> findAllProjectTasksGrouped(@Param("projectId") UUID projectId);

    @Query("""
            SELECT te FROM TaskEntity te
            JOIN te.project p
            WHERE te.taskId IN
                (SELECT t.taskId FROM UserTaskEntity ute
                    JOIN ute.task t
                    JOIN ute.user u
                    WHERE u.username = :username
                    AND t.status != 'FINISHED'
                )
            AND p.projectId = :projectId
            """)
    List<TaskEntity> findPagedMemberTasks(@Param("projectId") UUID projectId,@Param("username") String username, Pageable pageable);

    @Query("""
    SELECT count(t) FROM TaskEntity t
    WHERE t.taskCode IN
    (
        SELECT te.taskCode FROM UserTaskEntity ute
        JOIN ute.task te
        JOIN ute.user u
        WHERE ute.finished = true
        AND u.username = :username
    )
    """)
    Long countFinishedUserTasks(@Param("username")String username);
}
