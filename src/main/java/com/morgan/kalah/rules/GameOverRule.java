package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.IntStream;

/**
 * Checks if the pits are empty and the game is over
 */
@Slf4j
public class GameOverRule implements Rule {

    private final GameConfiguration gameConfiguration;
    private final Rule nextRule;

    GameOverRule(Rule nextRule, GameConfiguration gameConfiguration) {
        this.nextRule = nextRule;
        this.gameConfiguration = gameConfiguration;
    }

    /**
     * Checks if the game state is currently over because one of the players has no moves left
     *
     * @param game the game to play
     * @param move the move to play
     * @return The game, with the remaining seeds captured if the game is in end state
     */
    @Override
    public Game applyRule(Game game, int move) {

        Map<Integer, Integer> state = game.getState();

        if (playerPitsEmpty(Player.SOUTH, state) | playerPitsEmpty(Player.NORTH, state)) {
            log.debug("Game over, capture remaining seeds");
            captureRemainingSeeds(game);
        }

        if (nextRule != null) {
            return nextRule.applyRule(game, move);
        } else {
            return game;
        }
    }

    private void captureRemainingSeeds(Game game) {
        int northKalah = gameConfiguration.getNorthPlayerKalah();
        int southKalah = gameConfiguration.getSouthPlayerKalah();

        Map<Integer, Integer> state = game.getState();
        if (playerPitsEmpty(Player.NORTH, state)) {
            //Move all other seeds to south store
            IntStream.range(1, southKalah).boxed().forEach(pitId -> {
                int seeds = state.get(pitId);
                state.put(pitId, 0);

                int kalah = state.get(southKalah);
                state.put(southKalah, kalah + seeds);
            });
        } else {
            //Move all other seeds to north store
            IntStream.range(southKalah + 1, northKalah).boxed().forEach(pitId -> {
                int seeds = state.get(pitId);
                state.put(pitId, 0);

                int kalah = state.get(northKalah);
                state.put(northKalah, kalah + seeds);
            });
        }
    }

    private boolean playerPitsEmpty(Player player, Map<Integer, Integer> state) {
        int northKalah = gameConfiguration.getNorthPlayerKalah();
        int southKalah = gameConfiguration.getSouthPlayerKalah();

        if (Player.SOUTH == player) {
            return 0 == IntStream.range(1, southKalah)
                    .filter((x) -> state.get(x) > 0)
                    .count();
        } else {
            return 0 == IntStream.range(southKalah + 1, northKalah)
                    .filter((x) -> state.get(x) > 0)
                    .count();
        }
    }
}
