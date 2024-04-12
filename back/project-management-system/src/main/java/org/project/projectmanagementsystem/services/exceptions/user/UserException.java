package org.project.projectmanagementsystem.services.exceptions.user;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException{
    private HttpStatus httpStatus;
    public UserException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
// jwt token
// maven management