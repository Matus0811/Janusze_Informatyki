package com.project.projectmanagementsystem.database.repository;

import com.project.projectmanagementsystem.database.dao.UserDAO;
import com.project.projectmanagementsystem.database.entities.UserEntity;
import com.project.projectmanagementsystem.database.jpa.UserJpaRepository;
import com.project.projectmanagementsystem.database.mapper.UserEntityMapper;
import com.project.projectmanagementsystem.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository implements UserDAO {
    UserJpaRepository userJpaRepository;
    UserEntityMapper userEntityMapper;
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(userEntityMapper::mapFromEntity);
    }

    @Override
    public User save(User userToRegister) {
        UserEntity savedUser = userJpaRepository.save(userEntityMapper.mapToEntity(userToRegister));
        return userEntityMapper.mapFromEntity(savedUser);
    }
}
