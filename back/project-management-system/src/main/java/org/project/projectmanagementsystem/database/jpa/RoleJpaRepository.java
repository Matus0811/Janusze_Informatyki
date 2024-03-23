package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleJpaRepository extends JpaRepository<RoleEntity,Long> {
}
