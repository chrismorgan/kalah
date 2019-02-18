package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;

import static com.morgan.kalah.model.Player.NORTH;
import static com.morgan.kalah.model.Player.SOUTH;

public class NextPlayerRule implements Rule {

    private final Rule nextRule;

    private final GameConfiguration gameConfiguration;

    NextPlayerRule(Rule nextRule, GameConfiguration gameConfiguration) {
        this.nextRule = nextRule;
        this.gameConfiguration = gameConfiguration;
    }

    @Override
    public Game applyRule(Game game, int move) {

        if (!landedInCurrentPlayersKalah(game)) {
            game.setActivePlayer(otherPlayer(game.getActivePlayer()));
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
            return true;
        }
        if (SOUTH == game.getActivePlayer() && gameConfiguration.getSouthPlayerKalah() == lastPitSeeded) {
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
