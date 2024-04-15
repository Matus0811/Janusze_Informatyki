package org.project.projectmanagementsystem.services.exceptions.project;

import org.springframework.http.HttpStatus;

public class ActiveProjectLimitException extends ProjectException {
    public ActiveProjectLimitException(String message, HttpStatus httpStatus) {
        super(message,httpStatus);
    }
}
