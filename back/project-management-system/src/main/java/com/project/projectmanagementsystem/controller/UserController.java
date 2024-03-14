package com.project.projectmanagementsystem.controller;

import com.project.projectmanagementsystem.controller.dto.UserDTO;
import com.project.projectmanagementsystem.domain.User;
import com.project.projectmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Long userId){
        User user = userService.getUser(userId);

        return UserDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

}
