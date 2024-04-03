package org.project.projectmanagementsystem.services.exceptions.project;

public class ActiveProjectLimitException extends ProjectException {
    public ActiveProjectLimitException(String message) {
        super(message);
    }
}
