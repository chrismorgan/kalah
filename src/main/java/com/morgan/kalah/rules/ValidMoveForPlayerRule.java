package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.exception.InvalidMoveForPlayerException;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;

import static com.morgan.kalah.model.Player.NORTH;
import static com.morgan.kalah.model.Player.SOUTH;

public class ValidMoveForPlayerRule implements Rule {

    private final Rule nextRule;

    private final GameConfiguration gameConfiguration;

    ValidMoveForPlayerRule(Rule nextRule, GameConfiguration gameConfiguration) {
        this.nextRule = nextRule;
        this.gameConfiguration = gameConfiguration;
    }

    @Override
    public Game applyRule(Game game, int move) {

        if (!isValidFor(game.getActivePlayer(), move) || !isValidMoveNumber(move)) {
            throw new InvalidMoveForPlayerException(game.getActivePlayer(), move);
        }
        if (nextRule != null) {
            return nextRule.applyRule(game, move);
        } else {
            return game;
        }
    }

    private boolean isValidMoveNumber(int move) {
        return move <= gameConfiguration.getPits() && move > 0;
    }

    private boolean isValidFor(Player player, int move) {
        if (NORTH == player) {
            if (move < gameConfiguration.getNorthPlayerKalah() &&
                    move > gameConfiguration.getSouthPlayerKalah()) {
                return true;
            }
        } else if (SOUTH == player) {
            if (move < gameConfiguration.getSouthPlayerKalah() && move > 0) {
                return true;
            }
        }
        return false;
    }


}
