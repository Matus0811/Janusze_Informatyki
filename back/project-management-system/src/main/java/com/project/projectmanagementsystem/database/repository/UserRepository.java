package com.project.projectmanagementsystem.database.repository;

import com.project.projectmanagementsystem.database.dao.UserDAO;
import com.project.projectmanagementsystem.database.entities.UserEntity;
import com.project.projectmanagementsystem.database.jpa.UserJpaRepository;
import com.project.projectmanagementsystem.database.mapper.UserEntityMapper;
import com.project.projectmanagementsystem.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserDAO {
    private final UserJpaRepository userJpaRepository;
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserEntityMapper.INSTANCE::mapFromEntity);
    }

    @Override
    public User save(User userToRegister) {
        UserEntity savedUser = userJpaRepository.save(UserEntityMapper.INSTANCE.mapToEntity(userToRegister));
        return UserEntityMapper.INSTANCE.mapFromEntity(savedUser);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntityMapper.INSTANCE::mapFromEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> byUsername = userJpaRepository.findByUsername(username);
        return byUsername.map(UserEntityMapper.INSTANCE::mapFromEntity);
    }
}
