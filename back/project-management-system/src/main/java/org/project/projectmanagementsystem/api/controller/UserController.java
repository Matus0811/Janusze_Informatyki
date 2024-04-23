package org.project.projectmanagementsystem.api.controller;

import org.project.projectmanagementsystem.api.dto.CredentialsDTO;
import org.project.projectmanagementsystem.api.dto.TokenDTO;
import org.project.projectmanagementsystem.api.dto.UserDTO;
import org.project.projectmanagementsystem.api.dto.UserDataDTO;
import org.project.projectmanagementsystem.configuration.UserAuthProvider;
import org.project.projectmanagementsystem.domain.Role;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.mapper.CredentialsMapper;
import org.project.projectmanagementsystem.domain.mapper.UserMapper;
import org.project.projectmanagementsystem.services.UserProjectRoleService;
import org.project.projectmanagementsystem.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserAuthProvider userAuthProvider;
    private final UserProjectRoleService userProjectRoleService;

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Long userId){
        User user = userService.getUser(userId);

        return UserMapper.INSTANCE.mapFromDomainToDto(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO user){
        UserDTO registeredUser = UserMapper.INSTANCE.mapFromDomainToDto(
                userService.registerUser(UserMapper.INSTANCE.mapFromDtoToDomain(user))
        );

        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody @Valid CredentialsDTO credentials){
        User loggedUser = userService.login(
                CredentialsMapper.INSTANCE.mapFromDtoToDomain(credentials)
        );
        List<Role> userRoles = userProjectRoleService.findAllUserRoles(loggedUser);
        loggedUser = loggedUser.withRoles(userRoles.stream().map(Role::getName).collect(Collectors.toList()));

        TokenDTO token = TokenDTO.builder().token(userAuthProvider.createToken(loggedUser)).build();

        log.info("User [{}] logged in.",loggedUser);
        return new ResponseEntity<>(token,HttpStatus.OK);
    }
}
