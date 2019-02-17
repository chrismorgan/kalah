package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.exception.InvalidMoveForPlayerException;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.morgan.kalah.model.Player.NORTH;
import static com.morgan.kalah.model.Player.SOUTH;

@Component
public class ValidMoveForPlayerRule implements Rule {

    private Rule nextRule;

    @Autowired
    private GameConfiguration gameConfiguration;

    ValidMoveForPlayerRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    @Override
    public Game applyRule(Game game, int move) {

        if (!isValidFor(game.getActivePlayer(), move)) {
            throw new InvalidMoveForPlayerException(game.getActivePlayer(), move);
        }
        if (nextRule != null) {
            return nextRule.applyRule(game, move);
        } else {
            return game;
        }
    }

    private boolean isValidFor(Player player, int move) {
        if (NORTH == player) {
            if (move < gameConfiguration.getNorthPlayerKalah() &&
                    move > gameConfiguration.getSouthPlayerKalah()) {
                return true;
            }
        } else if (SOUTH == player) {
            if (move < gameConfiguration.getSouthPlayerKalah() && move > 1) {
                return true;
            }
        }
        return false;
    }


}
