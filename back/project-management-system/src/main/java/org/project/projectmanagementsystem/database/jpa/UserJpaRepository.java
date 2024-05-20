package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.UserEntity;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);

    @Query("""
        SELECT u FROM UserProjectRoleEntity upr
        RIGHT JOIN upr.user u
        JOIN upr.project p
        WHERE p.projectId != :projectId
    """)
    List<UserEntity> findUsersUnassignedToProject(@Param("projectId") UUID projectId);

    @Query("""
    SELECT u FROM UserEntity u where u.username in :usernames
    """)
    List<UserEntity> findAllByUsername(@Param("usernames") List<String> usernames);

    @Query("""
    SELECT DISTINCT u FROM UserEntity u
    WHERE u.userId NOT IN
    (
        SELECT ut.userId FROM UserProjectRoleEntity upr
        JOIN upr.user ut
        JOIN upr.project p
        WHERE p.projectId = :projectId
    )
    AND LOWER(u.username) LIKE LOWER(CONCAT(:username,'%'))
    """)
    List<UserEntity> findUnassignedUsersToProject(@Param("projectId") UUID projectId,@Param("username") String username, Pageable pageable);
}
