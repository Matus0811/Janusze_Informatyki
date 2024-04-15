package org.project.projectmanagementsystem.services.exceptions.project;

import org.springframework.http.HttpStatus;

public class ProjectNotFoundException extends ProjectException {
    public ProjectNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
