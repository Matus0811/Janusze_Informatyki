package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.BugEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BugJpaRepository extends JpaRepository<BugEntity,Long> {

    @Query("""
    SELECT be FROM BugEntity be
    JOIN FETCH be.project p
    JOIN FETCH be.bugTask t
    WHERE p.projectId = :projectId
    AND be.bugStatus = 'IN_REPAIR'
    AND t.taskId = :taskId
    """)
    Optional<BugEntity> findBugWithProjectIdAndTask(@Param("projectId") UUID projectId, @Param("taskId") Long taskId);

    @Query("""
    SELECT b FROM BugEntity b
    JOIN b.project p
    WHERE p.projectId = :projectId
    """)
    List<BugEntity> findBugsForProject(@Param("projectId") UUID projectId, Pageable pageable);

    @Query("""
    SELECT count(b) FROM BugEntity b
    JOIN b.project p
    WHERE p.projectId = :projectId
    """)
    Long countProjectReportedBugs(@Param("projectId") UUID projectId);

    @Query("""
    SELECT b FROM BugEntity b
    JOIN b.taskEntity t
    WHERE t.taskCode = :taskCode
    """)
    List<BugEntity> findBugsForTask(@Param("taskCode") String taskCode, Pageable pageable);

    Optional<BugEntity> findBySerialNumber(String serialNumber);
}
