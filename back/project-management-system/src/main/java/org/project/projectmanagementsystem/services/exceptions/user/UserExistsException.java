package org.project.projectmanagementsystem.services.exceptions.user;

import org.springframework.http.HttpStatus;

public class UserExistsException extends UserException{
    public UserExistsException(String message, HttpStatus status) {
        super(message, status);
    }
}
