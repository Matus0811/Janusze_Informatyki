package org.project.projectmanagementsystem.services.exceptions.project;

import org.project.projectmanagementsystem.services.exceptions.AppException;
import org.springframework.http.HttpStatus;

public class ProjectException extends AppException {
    public ProjectException(String message, HttpStatus status) {
        super(message,status);
    }
}
