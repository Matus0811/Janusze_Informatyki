package org.project.projectmanagementsystem.database.jpa;

import org.project.projectmanagementsystem.database.entities.UserTaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskJpaRepository extends JpaRepository<UserTaskEntity,Long> {

}
