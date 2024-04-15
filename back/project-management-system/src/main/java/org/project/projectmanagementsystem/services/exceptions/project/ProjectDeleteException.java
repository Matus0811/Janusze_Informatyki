package org.project.projectmanagementsystem.services.exceptions.project;

import org.springframework.http.HttpStatus;

public class ProjectDeleteException extends ProjectException {
    public ProjectDeleteException(String message, HttpStatus status) {
        super(message, status);
    }
}
