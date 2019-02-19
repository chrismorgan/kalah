package com.morgan.kalah.rules;

import com.morgan.kalah.exception.EmptyPitException;
import com.morgan.kalah.model.Game;

import java.util.Map;

/**
 * Checks if the pit to be played is already empty and therefore invalid
 */
public class EmptyPitRule implements Rule {

    private final Rule nextRule;

    EmptyPitRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    /**
     * Checks if the pit is empty
     *
     * @param game The game to play
     * @param move The pit to play
     * @return The game
     * @throws EmptyPitException if the pit is empty
     */
    @Override
    public Game applyRule(Game game, int move) {

        Map<Integer, Integer> state = game.getState();
        if (state.get(move) < 1) {
            throw new EmptyPitException(move);
        }

        if (nextRule != null) {
            return nextRule.applyRule(game, move);
        } else {
            return game;
        }
    }
}
