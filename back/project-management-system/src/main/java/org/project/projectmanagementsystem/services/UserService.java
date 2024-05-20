package org.project.projectmanagementsystem.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.projectmanagementsystem.database.UserRepository;
import org.project.projectmanagementsystem.domain.Credentials;
import org.project.projectmanagementsystem.domain.Project;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.services.exceptions.user.IncorrectPasswordException;
import org.project.projectmanagementsystem.services.exceptions.user.UserExistsException;
import org.project.projectmanagementsystem.services.exceptions.user.UserNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserProjectRoleService userProjectRoleService;

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
        log.info("Start processing user registration. User email: [{}]", userToRegister.getEmail());

        if (userExists(userToRegister.getEmail())) {
            log.error("Error during register user. User with email: [{}] exists", userToRegister.getEmail());
            throw new UserExistsException(
                    "User with given email: [%s] already exists".formatted(userToRegister.getEmail()),
                    HttpStatus.CONFLICT
            );
        }
        String encodedPassword = passwordEncoder.encode(userToRegister.getPassword());
        Role registeredUserRole = roleService.findRoleByName("USER");

        userToRegister = userToRegister
                .withPassword(encodedPassword);

        User savedUser = userRepository.save(userToRegister);
        userProjectRoleService.addUserProjectRole(savedUser, null, registeredUserRole);

        log.info("User [{}] registered successfully", savedUser);
        return savedUser;
    }


    private boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public User login(Credentials credentials) {
        if (!credentials.isEmailLogin()) {
            log.info("Process login by username [{}]", credentials);
            return processUsernameLogin(credentials);
        }

        log.info("Process login by email [{}]", credentials);
        return processEmailLogin(credentials);

    }

    private User processEmailLogin(Credentials credentials) {
        log.info("Processing sign in by email, credentials: [{}]", credentials);
        User loggedUser = findByEmail(credentials.getLogin());

        if (!passwordEncoder.matches(credentials.getPassword(), loggedUser.getPassword())) {
            log.error("Gave wrong password: given: [{}], found: [{}]", credentials.getPassword(), loggedUser.getPassword());
            throw new IncorrectPasswordException(
                    "Wrong password for email: [%s]".formatted(credentials.getLogin()),
                    HttpStatus.NOT_FOUND
            );
        }

        return loggedUser;
    }

    private User processUsernameLogin(Credentials credentials) {
        log.info("Processing sign in by username, credentials: [{}]", credentials);
        User loggedUser = findByUsername(credentials.getLogin());

        if (!passwordEncoder.matches(credentials.getPassword(), loggedUser.getPassword())) {
            log.error("Gave wrong password: given: [{}], found: [{}]", credentials.getPassword(), loggedUser.getPassword());
            throw new IncorrectPasswordException(
                    "Wrong password!",
                    HttpStatus.NOT_FOUND
            );
        }
        return loggedUser;

    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> {
                    log.error("Error finding user by email: [{}]", email);
                    return new UserNotFoundException(
                            "User with given email not found",
                            HttpStatus.NOT_FOUND
                    );
                });
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("Error during finding user by username: [{}]", username);
                    return new UserNotFoundException(
                            "User with given username not found",
                            HttpStatus.NOT_FOUND
                    );
                });
    }

    public List<User> findUsersWithGivenUsernames(List<String> usernames) {
        return userRepository.findUsersWithUsernames(usernames);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException(
                        "User  not found!",
                        HttpStatus.NOT_FOUND
                )
        );
    }

    public List<User> findUnassignedUsersToProject(Project project, String username, Pageable pageable) {
        return userRepository.findUnassignedUsersToProject(project.getProjectId(),username,pageable);
    }
}
