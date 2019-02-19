package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import lombok.extern.slf4j.Slf4j;

import static com.morgan.kalah.model.Player.NORTH;
import static com.morgan.kalah.model.Player.SOUTH;

/**
 * Determines who should play next
 */
@Slf4j
public class NextPlayerRule implements Rule {

    private final Rule nextRule;

    private final GameConfiguration gameConfiguration;

    NextPlayerRule(Rule nextRule, GameConfiguration gameConfiguration) {
        this.nextRule = nextRule;
        this.gameConfiguration = gameConfiguration;
    }

    /**
     * Checks if the last move ended in the current player's kalah and gives an extra turn
     * otherwise switches to the opposite player
     *
     * @param game The game to play
     * @param move The move to play
     * @return The game with the active player set accordingly
     */
    @Override
    public Game applyRule(Game game, int move) {

        if (!landedInCurrentPlayersKalah(game)) {
            game.setActivePlayer(otherPlayer(game.getActivePlayer()));
            log.debug("New active player is " + game.getActivePlayer());
        }

        if (nextRule != null) {
            return nextRule.applyRule(game, move);
        } else {
            return game;
        }
    }

    private boolean landedInCurrentPlayersKalah(Game game) {

        int lastPitSeeded = game.getLastPitPlayed();
        if (NORTH == game.getActivePlayer() && gameConfiguration.getNorthPlayerKalah() == lastPitSeeded) {
            log.debug("NORTH played into North Kalah, has extra turn");
            return true;
        }
        if (SOUTH == game.getActivePlayer() && gameConfiguration.getSouthPlayerKalah() == lastPitSeeded) {
            log.debug("SOUTH played into South Kalah, has extra turn");
            return true;
        }
        return false;
    }

    private Player otherPlayer(Player player) {
        if (NORTH == player) {
            return SOUTH;
        } else {
            return NORTH;
        }
    }
}
