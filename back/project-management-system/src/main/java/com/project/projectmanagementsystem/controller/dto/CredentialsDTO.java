package com.project.projectmanagementsystem.controller.dto;

public record CredentialsDTO(String username, String password, String email){
    public boolean isUsernameLogin(){
        return !this.username.isBlank() && !password.isBlank() && email.isBlank();
    }

    public boolean isEmailLogin(){
        return this.username.isBlank() && !password.isBlank() && !email.isBlank();
    }
}
