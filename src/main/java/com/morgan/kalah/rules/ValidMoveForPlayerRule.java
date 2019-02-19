package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.exception.InvalidMoveForPlayerException;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import lombok.extern.slf4j.Slf4j;

import static com.morgan.kalah.model.Player.NORTH;
import static com.morgan.kalah.model.Player.SOUTH;

/**
 * Checks if the proposed move is allowed for this user
 */
@Slf4j
public class ValidMoveForPlayerRule implements Rule {

    private final Rule nextRule;

    private final GameConfiguration gameConfiguration;

    ValidMoveForPlayerRule(Rule nextRule, GameConfiguration gameConfiguration) {
        this.nextRule = nextRule;
        this.gameConfiguration = gameConfiguration;
    }

    /**
     * Checks if the proposed move is a valid one
     *
     * @param game The game to play
     * @param move The move to play
     * @return The game
     * @throws InvalidMoveForPlayerException if the move is out of rang for that player
     */
    @Override
    public Game applyRule(Game game, int move) {

        if (!isValidFor(game.getActivePlayer(), move) | !isValidMoveNumber(move)) {
            throw new InvalidMoveForPlayerException(game.getActivePlayer(), move);
        }
        if (nextRule != null) {
            return nextRule.applyRule(game, move);
        } else {
            return game;
        }
    }

    private boolean isValidMoveNumber(int move) {
        log.debug("Checking " + move + " is within range");
        return move <= gameConfiguration.getPits() && move > 0;
    }

    private boolean isValidFor(Player player, int move) {
        if (NORTH == player) {
            if (move < gameConfiguration.getNorthPlayerKalah() &&
                    move > gameConfiguration.getSouthPlayerKalah()) {
                log.debug("Valid move for North " + move);
                return true;
            }
        } else if (SOUTH == player) {
            if (move < gameConfiguration.getSouthPlayerKalah() && move > 0) {
                log.debug("Valid move for South " + move);
                return true;
            }
        }
        return false;
    }


}
