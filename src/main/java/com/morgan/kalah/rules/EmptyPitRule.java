package com.morgan.kalah.rules;

import com.morgan.kalah.exception.EmptyPitException;
import com.morgan.kalah.model.Game;

import java.util.Map;

public class EmptyPitRule implements Rule {

    private final Rule nextRule;

    EmptyPitRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

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
