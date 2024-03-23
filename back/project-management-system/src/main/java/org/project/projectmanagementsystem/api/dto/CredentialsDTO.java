package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.api.annotations.ValidEmail;
import jakarta.validation.constraints.Size;

public record CredentialsDTO(String username, @Size(min = 8) String password, @ValidEmail String email){

}
