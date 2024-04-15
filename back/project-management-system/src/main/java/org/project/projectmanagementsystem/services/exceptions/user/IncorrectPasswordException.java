package org.project.projectmanagementsystem.services.exceptions.user;

import org.springframework.http.HttpStatus;

public class IncorrectPasswordException extends UserException {
    public IncorrectPasswordException(String message, HttpStatus status) {
        super(message,status);
    }
}
