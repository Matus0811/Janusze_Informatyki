package org.project.projectmanagementsystem.services.exceptions.task;

import org.project.projectmanagementsystem.services.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class TaskException extends AppException {
    public TaskException(String message, HttpStatus status) {
        super(message,status);
    }
}
