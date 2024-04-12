package org.project.projectmanagementsystem.services.exceptions.task;

import org.springframework.http.HttpStatus;

public class TaskNotFoundException extends TaskException {
    public TaskNotFoundException(String message, HttpStatus httpStatus) {
        super(message,httpStatus);
    }
}
