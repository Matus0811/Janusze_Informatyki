package org.project.projectmanagementsystem.services.exceptions.bug;

import org.project.projectmanagementsystem.services.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class BugException extends AppException {
    public BugException(String message, HttpStatus status) {
        super(message, status);
    }
}
