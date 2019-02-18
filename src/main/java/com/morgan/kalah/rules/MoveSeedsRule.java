package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;

import java.util.Map;
import java.util.stream.IntStream;

import static com.morgan.kalah.model.Player.NORTH;

public class MoveSeedsRule implements Rule {

    private final Rule nextRule;

    private final GameConfiguration gameConfiguration;

    MoveSeedsRule(Rule nextRule, GameConfiguration gameConfiguration) {
        this.gameConfiguration = gameConfiguration;
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

        //Take seeds from the pit
        final int seeds = game.getState().get(move);
        state.put(move, 0);

        //Distribute seeds
        IntStream.iterate(move + 1, (x) -> {
            if (x == otherPlayerKalah(game.getActivePlayer()) - 1) {
                x++;
            }
            return gameConfiguration.getPits() == x ? 1 : x + 1;

        })
                .limit(seeds)
                .forEach(pit -> {
                    int currentSeeds = state.get(pit);
                    state.put(pit, currentSeeds + 1);
                    game.setLastPitPlayed(pit);
                });
    }

    private int otherPlayerKalah(Player player) {
        if (NORTH == player) {
            return gameConfiguration.getSouthPlayerKalah();
        } else {
            return gameConfiguration.getNorthPlayerKalah();
        }
    }


}
