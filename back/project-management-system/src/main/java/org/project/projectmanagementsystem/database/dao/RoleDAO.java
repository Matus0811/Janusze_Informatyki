package org.project.projectmanagementsystem.database.dao;

import org.project.projectmanagementsystem.domain.Role;

import java.util.Optional;

public interface RoleDAO {
    Optional<Role> findByName(String roleName);
}
