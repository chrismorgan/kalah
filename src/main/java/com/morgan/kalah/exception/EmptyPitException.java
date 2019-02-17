package com.morgan.kalah.exception;

public class EmptyPitException extends RuntimeException {
    public EmptyPitException(int move) {
        super("You cannot play pit " + move + " because it is empty");
    }
}
