package com.project.projectmanagementsystem.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    String username;
    String password;
    String name;
    String surname;
    String gender;
    String email;
    String phone;

}
