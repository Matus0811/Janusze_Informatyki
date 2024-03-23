package org.project.projectmanagementsystem.api.dto;

import org.project.projectmanagementsystem.api.annotations.ValidEmail;
import org.project.projectmanagementsystem.api.annotations.ValidPhoneNumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotNull
    String username;
    @NotNull
    @Size(min = 8)
    String password;
    @NotNull
    String name;
    @NotNull
    String surname;
    String gender;
    @NotNull
    @ValidEmail
    String email;
    @NotNull
    @ValidPhoneNumber
    String phone;
}
