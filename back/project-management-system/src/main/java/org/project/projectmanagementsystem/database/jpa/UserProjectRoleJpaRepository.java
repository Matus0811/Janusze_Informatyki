package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.ProjectEntity;
import org.project.projectmanagementsystem.database.entities.UserProjectRoleEntity;
import org.project.projectmanagementsystem.domain.UserProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserProjectRoleJpaRepository extends JpaRepository<UserProjectRoleEntity, Long> {
    @Query("""
            SELECT upr.project FROM UserProjectRoleEntity upr
            JOIN upr.user u
            JOIN upr.project p
            JOIN upr.role r
            WHERE upr.user.userId = :ownerId
            AND r.name = 'PROJECT_OWNER'
            AND p.projectStatus != 'FINISHED'
            """)
    List<ProjectEntity> findNotFinishedUserProjects(@Param("ownerId") Long ownerId);

    @Query("""
        SELECT upr FROM UserProjectRoleEntity upr
        JOIN upr.user u
        JOIN upr.project p
        WHERE p.projectId != :projectId
    """)
    List<UserProjectRole> findUsersUnassignedToProject(@Param("projectId") UUID projectId);
}
