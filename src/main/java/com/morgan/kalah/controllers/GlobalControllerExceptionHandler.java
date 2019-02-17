package com.morgan.kalah.controllers;

import com.morgan.kalah.exception.GameNotFoundException;
import com.morgan.kalah.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<ErrorMessage> handleException(GameNotFoundException exception) {
        return responseEntityFromException(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorMessage> responseEntityFromException(Exception exception, HttpStatus status) {
        return new ResponseEntity<>(new ErrorMessage().message(exception.getMessage()), status);
    }
}
