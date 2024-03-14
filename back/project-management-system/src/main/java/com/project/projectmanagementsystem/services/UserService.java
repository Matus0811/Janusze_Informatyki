package com.project.projectmanagementsystem.services;

import com.project.projectmanagementsystem.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    public User getUser(Long id){
        return User.builder()
                .userId(id)
                .username(String.format("username%s",id))
                .password(String.format("password%s",id))
                .name(String.format("USER%s",id))
                .surname(String.format("surname%s",id))
                .email(String.format("email%s@mail.com",id))
                .phone(String.format("0000%s",id))
                .build();
    }
}
