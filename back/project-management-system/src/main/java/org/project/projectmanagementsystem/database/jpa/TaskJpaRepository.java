package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.TaskEntity;
import org.project.projectmanagementsystem.domain.Task;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

}
