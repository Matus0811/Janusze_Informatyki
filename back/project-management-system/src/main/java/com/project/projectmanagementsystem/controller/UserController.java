package com.project.projectmanagementsystem.controller;

import com.project.projectmanagementsystem.controller.dto.CredentialsDTO;
import com.project.projectmanagementsystem.controller.dto.UserDTO;
import com.project.projectmanagementsystem.domain.User;
import com.project.projectmanagementsystem.domain.mapper.UserMapper;
import com.project.projectmanagementsystem.services.UserService;
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

        return UserMapper.INSTANCE.mapFromDomainUser(user);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody @Valid UserDTO user){
        UserDTO registeredUser = UserMapper.INSTANCE.mapFromDomainUser(userService.registerUser(user));


        return new ResponseEntity<>(registeredUser,HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody @Valid CredentialsDTO credentials){
        UserDTO loggedUser = UserMapper.INSTANCE.mapFromDomainUser(userService.login(credentials));

        log.info("User [{}] logged in.",loggedUser);
        return new ResponseEntity<>(loggedUser,HttpStatus.OK);
    }
}
