package org.project.projectmanagementsystem.services.exceptions.user;

import org.project.projectmanagementsystem.services.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class UserException extends AppException {
    public UserException(String message, HttpStatus httpStatus) {
        super(message,httpStatus);
    }
}
// jwt token
// maven management