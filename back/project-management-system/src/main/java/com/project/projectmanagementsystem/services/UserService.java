package com.project.projectmanagementsystem.services;

import com.project.projectmanagementsystem.controller.dto.CredentialsDTO;
import com.project.projectmanagementsystem.controller.dto.UserDTO;
import com.project.projectmanagementsystem.database.dao.UserDAO;
import com.project.projectmanagementsystem.domain.User;
import com.project.projectmanagementsystem.domain.exceptions.IncorrectPasswordException;
import com.project.projectmanagementsystem.domain.exceptions.UserExistsException;
import com.project.projectmanagementsystem.domain.exceptions.UserNotFoundException;
import com.project.projectmanagementsystem.domain.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public User getUser(Long id){
        return User.builder()
                .userId(id)
                .username("user"+id)
                .password("user"+id)
                .name("user")
                .surname("surname")
                .gender(User.Gender.MALE)
                .phone("+48 374 173 857")
                .build();

//        return userDAO.findById(id).orElseThrow(
//                () -> new UserNotFoundException("User with id [%s] not found".formatted(id))
//        );
    }

    @Transactional
    public User registerUser(UserDTO user) {
        User userToRegister = UserMapper.INSTANCE.mapToDomainUser(user);

        if(!userExists(userToRegister.getEmail())){
            return userDAO.save(userToRegister);
        }

        throw new UserExistsException(
                "User with given email: [%s] already exists".formatted(userToRegister.getEmail())
        );
    }

    private boolean userExists(String email) {
        return userDAO.findByEmail(email).isPresent();
    }

    @Transactional
    public User login(CredentialsDTO credentials) {
        if(credentials.isUsernameLogin()){
            return processUsernameLogin(credentials);
        }

        if(credentials.isEmailLogin()){
            return processEmailLogin(credentials);
        }

        throw new UserNotFoundException("User with given credentials [%s] not found".formatted(credentials));
    }

    private User processEmailLogin(CredentialsDTO credentials) {
        Optional<User> loggedUserByEmail = userDAO.findByEmail(credentials.email());

        User user = loggedUserByEmail.orElseThrow(
                () -> new UserNotFoundException("User with given email [%s] not found".formatted(credentials.email()))
        );

        if(user.getPassword().equals(credentials.password())){
            return user;
        }

        throw new IncorrectPasswordException("Wrong password for email: [%s]".formatted(credentials.email()));
    }

    private User processUsernameLogin(CredentialsDTO credentials) {
        Optional<User> loggedUserByUsername = userDAO.findByUsername(credentials.username());

        User user = loggedUserByUsername.orElseThrow(
                () -> new UserNotFoundException("User with given username [%s] not found".formatted(credentials.username()))
        );

        if(user.getPassword().equals(credentials.password())){
            return user;
        }

        throw new IncorrectPasswordException("Wrong password for username: [%s]".formatted(credentials.username()));
    }
}
