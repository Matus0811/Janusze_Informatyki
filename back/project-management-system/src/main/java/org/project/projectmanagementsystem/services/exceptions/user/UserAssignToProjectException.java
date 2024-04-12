package org.project.projectmanagementsystem.services.exceptions.user;

import org.springframework.http.HttpStatus;

public class UserAssignToProjectException extends UserException{
    public UserAssignToProjectException(String message, HttpStatus httpStatus) {
        super(message,httpStatus);
    }
}
