package com.morgan.kalah.exception;

import com.morgan.kalah.model.Player;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMoveForPlayerException extends RuntimeException {

    public InvalidMoveForPlayerException(Player player, int move) {
        super("Unable to play position " + move + " for player " + player.toString());
    }
}
