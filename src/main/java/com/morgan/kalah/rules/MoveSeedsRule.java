package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.IntStream;

@Component
public class MoveSeedsRule implements Rule {

    private final Rule nextRule;

    @Autowired
    private GameConfiguration gameConfiguration;

    MoveSeedsRule(Rule nextRule) {
        this.nextRule = nextRule;
    }

    @Override
    public Game applyRule(Game game, int move) {

        playMove(game, move);

        if (nextRule != null) {
            return nextRule.applyRule(game, move);
        } else {
            return game;
        }
    }

    private void playMove(Game game, int move) {
        Map<Integer, Integer> state = game.getState();
        final int seeds = game.getState().get(move);

        state.put(move, 0);
        IntStream.iterate(move, (x) -> {
            if (gameConfiguration.getPits() == x) {
                return 1;
            } else {
                return x + 1;
            }
        }).limit(seeds).forEach(pit -> {
            int currentSeeds = state.get(pit);
            state.put(pit, currentSeeds + 1);

        });

    }
}
