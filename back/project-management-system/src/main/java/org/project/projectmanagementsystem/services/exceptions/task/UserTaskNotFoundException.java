package org.project.projectmanagementsystem.services.exceptions.task;

import org.springframework.http.HttpStatus;

public class UserTaskNotFoundException extends TaskException {
    public UserTaskNotFoundException(String message, HttpStatus status) {
        super(message,status);
    }
}
