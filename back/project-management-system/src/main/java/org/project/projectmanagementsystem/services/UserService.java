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

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserProjectRoleService userProjectRoleService;

    public User getUser(Long id) {
        return findById(id);
    }


    @Transactional
    public User registerUser(User userToRegister) {
        log.info("Start processing user registration. User email: [{}]", userToRegister.getEmail());

        String userToRegisterUsername = userToRegister.getUsername();

        if(userRepository.findByUsername(userToRegisterUsername).isPresent()){
            log.error("Error during user registration. User with username: [{}] exists",userToRegister);
            throw new UserExistsException(
                    "Podana nazwa użytkownika [%s] jest zajęta".formatted(userToRegisterUsername),
                    HttpStatus.CONFLICT
            );
        }

        String userToRegisterEmail = userToRegister.getEmail();

        if (userRepository.findByEmail(userToRegisterEmail).isPresent()) {
            log.error("Error during register user. User with email: [{}] exists", userToRegisterEmail);
            throw new UserExistsException(
                    "Email %s został już użyty".formatted(userToRegisterEmail),
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
                    "Nieprawidłowe hasło dla użytkownika %s".formatted(credentials.getLogin()),
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
                    "Nieprawidłowe hasło!",
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
                            "Podany adres email jest nieprawidłowy",
                            HttpStatus.NOT_FOUND
                    );
                });
    }

    public User findByUsername(String username) {
        log.info("Searching user with username: [{}]",username);
        return userRepository.findByUsername(username).orElseThrow(
                () -> {
                    log.error("Failed searching user with username: [{}]", username);
                    return new UserNotFoundException(
                            "Nieprawidłowa nazwa użytkownika",
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
                        "Nie znaleziono użytkownika",
                        HttpStatus.NOT_FOUND
                )
        );
    }

    public List<User> findUnassignedUsersToProject(Project project, String username, Pageable pageable) {
        return userRepository.findUnassignedUsersToProject(project.getProjectId(),username,pageable);
    }

    public List<User> findUsersAssignedToProject(UUID projectId) {
        return userRepository.findUsersAssignedToProject(projectId);
    }

    public User findProjectOwner(UUID projectId) {
        return userRepository.findProjectOwner(projectId).orElse(User.builder().build());
    }
}
