package org.project.projectmanagementsystem.database;

import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.database.entities.UserEntity;
import org.project.projectmanagementsystem.database.jpa.UserJpaRepository;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository{
    private final UserJpaRepository userJpaRepository;
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserMapper.INSTANCE::mapFromEntityToDomain);
    }

    public User save(User userToRegister) {
        UserEntity savedUser = userJpaRepository.save(UserMapper.INSTANCE.mapFromDomainToEntity(userToRegister));
        return UserMapper.INSTANCE.mapFromEntityToDomain(savedUser);
    }

    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserMapper.INSTANCE::mapFromEntityToDomain);
    }

    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> byUsername = userJpaRepository.findByUsername(username);
        return byUsername.map(UserMapper.INSTANCE::mapFromEntityToDomain);
    }
}
