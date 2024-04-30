package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.BugEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface BugJpaRepository extends JpaRepository<BugEntity,Long> {

    @Query("""
    SELECT be FROM BugEntity be
    JOIN FETCH be.project p
    WHERE p.projectId = :projectId
    """)
    Optional<BugEntity> findBugWithProjectId(@Param("projectId") UUID projectId);
}
