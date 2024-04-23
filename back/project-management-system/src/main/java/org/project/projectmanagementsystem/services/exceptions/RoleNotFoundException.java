package org.project.projectmanagementsystem.services.exceptions;

import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends AppException {
    public RoleNotFoundException(String message, HttpStatus status) {
        super(message,status);
    }
}
