package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.domain.User;

public record UserDataDTO (
        String username,
        String name,
        String surname,
        User.Gender gender,
        String email,
        String phone,
        String token
        ){
}
