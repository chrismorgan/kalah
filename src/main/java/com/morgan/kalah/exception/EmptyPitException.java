package com.morgan.kalah.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmptyPitException extends RuntimeException {
    public EmptyPitException(int move) {
        super("You cannot play pit " + move + " because it is empty");
    }
}
