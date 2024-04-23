package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.api.annotations.ValidEmail;
import jakarta.validation.constraints.Size;

public record CredentialsDTO(String login, @Size(min = 8) String password){

}
