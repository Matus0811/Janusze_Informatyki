package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.domain.User;

import java.util.List;

public record UserDTO(
        String username,
        String name,
        String surname,
        User.Gender gender,
        String email,
        String phone
) {
}
