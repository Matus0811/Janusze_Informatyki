package org.project.projectmanagementsystem.services.exceptions.project;

import org.springframework.http.HttpStatus;

public class ProjectAlreadyExistsException extends ProjectException {
    public ProjectAlreadyExistsException(String message, HttpStatus status) {
        super(message,status);
    }
}
