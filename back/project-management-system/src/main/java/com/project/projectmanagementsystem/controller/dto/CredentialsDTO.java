package com.project.projectmanagementsystem.controller.dto;

import com.project.projectmanagementsystem.controller.annotations.ValidEmail;
import jakarta.validation.constraints.Size;

public record CredentialsDTO(String username, @Size(min = 8) String password, @ValidEmail String email){

}
