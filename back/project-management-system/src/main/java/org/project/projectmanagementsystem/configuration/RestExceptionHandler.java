package org.project.projectmanagementsystem.configuration;

import org.project.projectmanagementsystem.services.exceptions.AppException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<String> handleException(AppException e){
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
