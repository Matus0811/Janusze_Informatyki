package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByName(String name);
}
