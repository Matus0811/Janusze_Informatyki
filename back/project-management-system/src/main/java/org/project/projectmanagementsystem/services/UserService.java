package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.projectmanagementsystem.database.UserRepository;
import org.project.projectmanagementsystem.domain.Credentials;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.services.exceptions.user.IncorrectPasswordException;
import org.project.projectmanagementsystem.services.exceptions.user.UserExistsException;
import org.project.projectmanagementsystem.services.exceptions.user.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(Long id) {
        return User.builder()
                .userId(id)
                .username("user" + id)
                .password("user" + id)
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
    public User registerUser(User userToRegister) {
        log.info("Start processing user registration. User data: [{}]", userToRegister);

        if (!userExists(userToRegister.getEmail())) {
            log.info("User [{}] registered successfully", userToRegister);
            return userRepository.save(userToRegister);
        }
        log.error("Error during register user. User already exists. Data [{}]", userToRegister);
        throw new UserExistsException(
                "User with given email: [%s] already exists".formatted(userToRegister.getEmail())
        );
    }

    private boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public User login(Credentials credentials) {
        if (credentials.isUsernameLogin()) {
            log.info("Process login by username [{}]", credentials);
            return processUsernameLogin(credentials);
        }

        if (credentials.isEmailLogin()) {
            log.info("Process login by email [{}]", credentials);
            return processEmailLogin(credentials);
        }

        throw new UserNotFoundException("User with given credentials [%s] not found".formatted(credentials));
    }

    private User processEmailLogin(Credentials credentials) {
        log.info("Processing sign in by email, credentials: [{}]", credentials);
        User loggedUser = findByEmail(credentials.getEmail());

        if (loggedUser.getPassword().equals(credentials.getPassword())) {
            log.info("Comparing user password, given: [{}], found: [{}]", credentials.getPassword(), loggedUser.getPassword());
            return loggedUser;
        }

        log.error("Gave wrong password: given: [{}], found: [{}]", credentials.getPassword(), loggedUser.getPassword());
        throw new IncorrectPasswordException("Wrong password for email: [%s]".formatted(credentials.getEmail()));
    }

    private User processUsernameLogin(Credentials credentials) {
        log.info("Processing sign in by username, credentials: [{}]", credentials);
        User loggedUser = findByUsername(credentials.getUsername());

        if (loggedUser.getPassword().equals(credentials.getPassword())) {
            log.info("Comparing user password, given: [{}], found: [{}]", credentials.getPassword(), loggedUser.getPassword());
            return loggedUser;
        }

        log.error("Gave wrong password: given: [{}], found: [{}]", credentials.getPassword(), loggedUser.getPassword());
        throw new IncorrectPasswordException("Wrong password for username: [%s]".formatted(credentials.getUsername()));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.error("Error finding user by email: [{}]", email);
                    return new UserNotFoundException("User with email [%s] not found".formatted(email));
                });
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("Error during finding user by username: [{}]", username);
                    return new UserNotFoundException("User with given username [%s] not found".formatted(username));
                });
    }
}
