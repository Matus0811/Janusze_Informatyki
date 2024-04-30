package org.project.projectmanagementsystem.services.exceptions;

import org.project.projectmanagementsystem.services.exceptions.bug.BugException;
import org.springframework.http.HttpStatus;

public class BugNotFoundException extends BugException {
    public BugNotFoundException(String message, HttpStatus httpStatus) {
        super(message,httpStatus);
    }
}
