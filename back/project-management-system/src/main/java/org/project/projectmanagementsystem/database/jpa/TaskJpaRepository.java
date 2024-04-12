package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskJpaRepository extends JpaRepository<TaskEntity,Long> {
    Optional<TaskEntity> findByTaskCode(String taskCode);
}
