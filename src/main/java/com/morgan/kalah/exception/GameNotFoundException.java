package com.morgan.kalah.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String gameId) {
        super("Game " + gameId + " does not exist");
    }
}
