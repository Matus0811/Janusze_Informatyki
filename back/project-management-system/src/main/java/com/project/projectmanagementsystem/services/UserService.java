package com.project.projectmanagementsystem.services;

import com.project.projectmanagementsystem.controller.dto.UserDTO;
import com.project.projectmanagementsystem.database.dao.UserDAO;
import com.project.projectmanagementsystem.domain.User;
import com.project.projectmanagementsystem.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public User getUser(Long id){
        return User.builder()
                .userId(id)
                .username(String.format("username%s",id))
                .password(String.format("password%s",id))
                .name(String.format("USER%s",id))
                .gender(User.Gender.MALE)
                .surname(String.format("surname%s",id))
                .email(String.format("email%s@mail.com",id))
                .phone(String.format("0000%s",id))
                .build();
    }

    @Transactional
    public User registerUser(UserDTO user) {
        User userToRegister = UserMapper.INSTANCE.mapToDomainUser(user);
        return userToRegister;
//        if(!userExists(userToRegister.getEmail())){
//            return userDAO.save(userToRegister);
//        }
//
//        throw new UserExistsException(
//                "User with given email: [%s] already exists".formatted(userToRegister.getEmail())
//        );
    }

    private boolean userExists(String email) {
        return userDAO.findByEmail(email).isPresent();
    }
}
