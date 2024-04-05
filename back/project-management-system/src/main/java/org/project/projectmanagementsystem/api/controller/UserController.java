package org.project.projectmanagementsystem.api.controller;

import org.project.projectmanagementsystem.api.dto.CredentialsDTO;
import org.project.projectmanagementsystem.api.dto.UserDTO;
import org.project.projectmanagementsystem.domain.User;
import org.project.projectmanagementsystem.domain.mapper.CredentialsMapper;
import org.project.projectmanagementsystem.domain.mapper.UserMapper;
import org.project.projectmanagementsystem.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
    public ResponseEntity<UserDTO> login(@RequestBody @Valid CredentialsDTO credentials){
        UserDTO loggedUser = UserMapper.INSTANCE.mapFromDomainToDto(
                userService.login(
                        CredentialsMapper.INSTANCE.mapFromDtoToDomain(credentials)
                )
        );

        log.info("User [{}] logged in.",loggedUser);
        return new ResponseEntity<>(loggedUser,HttpStatus.OK);
    }
}
