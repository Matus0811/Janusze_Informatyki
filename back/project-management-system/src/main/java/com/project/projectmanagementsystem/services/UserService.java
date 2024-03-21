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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
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
        log.info("Start processing user registration. User data: [{}]",user);
        User userToRegister = UserMapper.INSTANCE.mapToDomainUser(user);

        if(!userExists(userToRegister.getEmail())){
            log.info("User [{}] registered successfully",userToRegister);
            return userDAO.save(userToRegister);
        }
        log.error("Error during register user. User already exists. Data [{}]", userToRegister);
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
            log.info("Process login by username [{}]",credentials);
            return processUsernameLogin(credentials);
        }

        if(credentials.isEmailLogin()){
            log.info("Process login by email [{}]",credentials);
            return processEmailLogin(credentials);
        }

        throw new UserNotFoundException("User with given credentials [%s] not found".formatted(credentials));
    }

    private User processEmailLogin(CredentialsDTO credentials) {
        log.info("Processing sign in by email, credentials: [{}]",credentials);
        Optional<User> loggedUserByEmail = userDAO.findByEmail(credentials.email());

        User user = loggedUserByEmail.orElseThrow(
                () -> {
                    log.error("Error during processing user sign in using email, given credentials [{}]",credentials);
                    return new UserNotFoundException("User with given email [%s] not found".formatted(credentials.email()));
                }
        );

        if(user.getPassword().equals(credentials.password())){
            log.info("Comparing user password, given: [{}], found: [{}]",credentials.password(), user.getPassword());
            return user;
        }

        log.error("Gave wrong password: given: [{}], found: [{}]",credentials.password(), user.getPassword());
        throw new IncorrectPasswordException("Wrong password for email: [%s]".formatted(credentials.email()));
    }

    private User processUsernameLogin(CredentialsDTO credentials) {
        log.info("Processing sign in by username, credentials: [{}]",credentials);
        Optional<User> loggedUserByUsername = userDAO.findByUsername(credentials.username());

        User user = loggedUserByUsername.orElseThrow(
                () -> {
                    log.error("Error during processing user sign in using username, given credentials [{}]",credentials);
                    return new UserNotFoundException("User with given username [%s] not found".formatted(credentials.username()));
                }
        );

        if(user.getPassword().equals(credentials.password())){
            log.info("Comparing user password, given: [{}], found: [{}]",credentials.password(), user.getPassword());
            return user;
        }

        log.error("Gave wrong password: given: [{}], found: [{}]",credentials.password(), user.getPassword());
        throw new IncorrectPasswordException("Wrong password for username: [%s]".formatted(credentials.username()));
    }
}
