package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.ProjectEntity;
import org.project.projectmanagementsystem.database.entities.UserProjectRoleEntity;
import org.project.projectmanagementsystem.domain.UserProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
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
        SELECT DISTINCT upr FROM UserProjectRoleEntity upr
        JOIN upr.user u
        JOIN upr.project p
        WHERE p.projectId != :projectId
    """)
    List<UserProjectRoleEntity> findUsersUnassignedToProject(@Param("projectId") UUID projectId);
    @Query("""
    SELECT upre FROM UserProjectRoleEntity upre
    JOIN FETCH upre.project p
    JOIN fetch upre.user u
    JOIN FETCH upre.role r
    WHERE p.projectId = :projectId
    AND u.userId = :userId
    AND r.name = 'TEAM_MEMBER'
    """)
    Optional<UserProjectRoleEntity> findUserProjectRole(@Param("projectId") UUID projectId, @Param("userId") Long userId);

    @Query("""
    SELECT upre FROM UserProjectRoleEntity upre
    JOIN FETCH upre.project p
    JOIN FETCH upre.user u
    JOIN FETCH upre.role r
    WHERE p.projectStatus != "FINISHED"
    AND u.email = :email
    AND r.name = "TEAM_MEMBER"
    """)
    List<UserProjectRoleEntity> findAllUserProjectsAsMember(@Param("email") String email);
}
