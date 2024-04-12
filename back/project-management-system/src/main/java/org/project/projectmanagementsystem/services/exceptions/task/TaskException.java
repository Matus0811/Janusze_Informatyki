package org.project.projectmanagementsystem.services.exceptions.task;

import org.springframework.http.HttpStatus;

public class TaskException extends RuntimeException{
    private final HttpStatus httpStatus;
    public TaskException(String message, HttpStatus status) {
        super(message);
        this.httpStatus = status;
    }
}
