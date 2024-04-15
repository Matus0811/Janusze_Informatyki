package org.project.projectmanagementsystem.services.exceptions.project;

import org.springframework.http.HttpStatus;

public class ProjectException extends RuntimeException{
    private HttpStatus status;
    public ProjectException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
