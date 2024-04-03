package org.project.projectmanagementsystem.database;

import org.project.projectmanagementsystem.database.dao.UserDAO;
import org.project.projectmanagementsystem.database.entities.UserEntity;
import org.project.projectmanagementsystem.database.jpa.UserJpaRepository;
import org.project.projectmanagementsystem.domain.User;
import lombok.RequiredArgsConstructor;
import org.project.projectmanagementsystem.domain.mapper.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements UserDAO {
    private final UserJpaRepository userJpaRepository;
    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .map(UserMapper.INSTANCE::mapFromEntity);
    }

    @Override
    public User save(User userToRegister) {
        UserEntity savedUser = userJpaRepository.save(UserMapper.INSTANCE.mapFromDomain(userToRegister));
        return UserMapper.INSTANCE.mapFromEntity(savedUser);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserMapper.INSTANCE::mapFromEntity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Optional<UserEntity> byUsername = userJpaRepository.findByUsername(username);
        return byUsername.map(UserMapper.INSTANCE::mapFromEntity);
    }
}
