package org.project.projectmanagementsystem.services.exceptions;

import org.springframework.http.HttpStatus;

public class EmptyFormException extends AppException {
    public EmptyFormException(String message, HttpStatus httpStatus) {
        super(message,httpStatus);
    }
}
