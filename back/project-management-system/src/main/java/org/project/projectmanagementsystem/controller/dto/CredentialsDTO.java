package org.project.projectmanagementsystem.controller.dto;

import org.project.projectmanagementsystem.controller.annotations.ValidEmail;
import jakarta.validation.constraints.Size;

public record CredentialsDTO(String username, @Size(min = 8) String password, @ValidEmail String email){

}
