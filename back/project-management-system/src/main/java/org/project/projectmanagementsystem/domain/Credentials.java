package org.project.projectmanagementsystem.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Value
@Builder
public class Credentials {
    String username;
    String password;
    String email;

    public boolean isUsernameLogin(){
        return !this.username.isBlank() && !password.isBlank() && email.isBlank();
    }

    public boolean isEmailLogin(){
        return this.username.isBlank() && !password.isBlank() && !email.isBlank();
    }
}
