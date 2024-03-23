package org.project.projectmanagementsystem.database.dao;

import org.project.projectmanagementsystem.domain.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByEmail(String email);

    User save(User userToRegister);

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);
}
