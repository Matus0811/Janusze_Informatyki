package com.project.projectmanagementsystem.controller;

import com.project.projectmanagementsystem.controller.dto.UserDTO;
import com.project.projectmanagementsystem.domain.User;
import com.project.projectmanagementsystem.domain.mapper.UserMapper;
import com.project.projectmanagementsystem.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable("id") Long userId){
        User user = userService.getUser(userId);

        return UserMapper.INSTANCE.mapFromDomainUser(user);
    }

    // TODO need to fix this
    @PostMapping()
    public ResponseEntity<UserDTO> registerUser(@RequestBody Long something){
//        UserDTO registeredUser = UserMapper.INSTANCE.mapFromDomainUser(userService.registerUser(user));

        return new ResponseEntity<>(UserDTO.builder().name(something.toString()).build(), HttpStatus.CREATED);
    }

}
