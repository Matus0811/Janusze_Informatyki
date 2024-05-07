package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.ProjectEntity;
import org.project.projectmanagementsystem.database.entities.UserProjectRoleEntity;
import org.project.projectmanagementsystem.domain.UserProjectRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            WHERE upr.user.email = :email
            AND r.name = 'PROJECT_OWNER'
            AND p.projectStatus != 'FINISHED'
            """)
    Page<ProjectEntity> findNotFinishedUserProjects(@Param("email") String email, Pageable pageable);

    @Query(value = """
                SELECT upr FROM UserProjectRoleEntity upr
                WHERE upr.user.userId NOT IN (
                    SELECT upr2.user.userId FROM UserProjectRoleEntity upr2
                        WHERE upr2.project.projectId = :projectId
                )
                AND LOWER(upr.user.username) LIKE LOWER(CONCAT(:word,'%'))
            """)
    List<UserProjectRoleEntity> findUsersUnassignedToProject(@Param("projectId") UUID projectId,@Param("word") String word);
    @Query("""
    SELECT upre FROM UserProjectRoleEntity upre
    JOIN FETCH upre.project p
    JOIN fetch upre.user u
    JOIN FETCH upre.role r
    WHERE p.projectId = :projectId
    AND u.email = :email
    AND r.name = 'TEAM_MEMBER'
    """)
    Optional<UserProjectRoleEntity> findUserProjectRole(@Param("projectId") UUID projectId, @Param("email") String userId);

    @Query("""
    SELECT upre FROM UserProjectRoleEntity upre
    JOIN FETCH upre.project p
    JOIN FETCH upre.user u
    JOIN FETCH upre.role r
    WHERE p.projectStatus != 'FINISHED'
    AND u.email = :email
    AND r.name = 'TEAM_MEMBER'
    """)
    Page<UserProjectRoleEntity> findAllUserProjectsAsMember(@Param("email") String email, Pageable pageable);

    @Query("""
    SELECT DISTINCT upre FROM UserProjectRoleEntity upre
    JOIN FETCH upre.user u
    JOIN FETCH upre.role r
    WHERE u.userId = :userId
    """)
    List<UserProjectRoleEntity> findAllUserRoles(@Param("userId") Long userId);


    @Query(
    """
    DELETE FROM UserProjectRoleEntity upre
    WHERE upre.role.name = 'USER'
    AND upre.user.userId = :userId
    AND upre.project.projectId IS NULL
    """)
    @Modifying
    void removeDefaultUserRole(Long userId);

    @Query(
    """
    SELECT upre FROM UserProjectRoleEntity upre
    JOIN FETCH upre.project p
    JOIN FETCH upre.user u
    JOIN FETCH upre.role r
    WHERE u.email = :email AND r.name = 'PROJECT_OWNER'
    """)
    List<UserProjectRoleEntity> findAllUserProjectsAsOwner(@Param("email") String ownerEmail);

    @Query("""
    SELECT upre FROM UserProjectRoleEntity upre
    JOIN FETCH upre.project p
    JOIN FETCH upre.user u
    JOIN FETCH upre.role r
    WHERE p.projectId = :projectId
    AND r.name = 'TEAM_MEMBER'
    """)
    Page<UserProjectRoleEntity> findPagedProjectMembers(@Param("projectId") UUID projectId, Pageable pageable);
}
