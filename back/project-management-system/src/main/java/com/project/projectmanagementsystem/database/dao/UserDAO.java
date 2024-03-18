package com.project.projectmanagementsystem.database.dao;

import com.project.projectmanagementsystem.domain.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByEmail(String email);

    User save(User userToRegister);
}
