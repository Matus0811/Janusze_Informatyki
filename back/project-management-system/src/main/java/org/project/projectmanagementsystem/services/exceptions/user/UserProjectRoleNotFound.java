package org.project.projectmanagementsystem.services.exceptions.user;

import org.springframework.http.HttpStatus;

public class UserProjectRoleNotFound extends UserException {
    public UserProjectRoleNotFound(String message, HttpStatus httpStatus) {
        super(message,httpStatus);
    }
}
