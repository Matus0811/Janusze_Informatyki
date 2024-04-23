package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.BugEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugJpaRepository extends JpaRepository<BugEntity,Long> {
}
