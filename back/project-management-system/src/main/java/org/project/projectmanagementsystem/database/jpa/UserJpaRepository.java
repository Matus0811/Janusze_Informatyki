package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity,Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);

    @Query("""
    SELECT u FROM UserEntity u where u.email in :emails
    """)
    List<UserEntity> findAllByEmail(@Param("emails") List<String> emails);
}
