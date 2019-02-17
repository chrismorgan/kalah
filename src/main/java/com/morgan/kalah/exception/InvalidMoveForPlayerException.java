package com.morgan.kalah.exception;

import com.morgan.kalah.model.Player;

public class InvalidMoveForPlayerException extends RuntimeException {

    public InvalidMoveForPlayerException(Player player, int move) {
        super("Unable to play position " + move + " for player " + player.toString());
    }
}
