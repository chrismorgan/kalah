package com.morgan.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadInputException extends RuntimeException {
    public BadInputException(String message, Throwable cause) {
        super("Input not valid: " + message, cause);
    }
}
