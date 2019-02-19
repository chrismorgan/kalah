package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.IntStream;

import static com.morgan.kalah.model.Player.NORTH;

/**
 * Performs the move in the game returning the game state after the pit contents have changed
 */
@Slf4j
public class MoveSeedsRule implements Rule {

    private final Rule nextRule;

    private final GameConfiguration gameConfiguration;

    MoveSeedsRule(Rule nextRule, GameConfiguration gameConfiguration) {
        this.gameConfiguration = gameConfiguration;
        this.nextRule = nextRule;
    }

    /**
     * Plays the given move, sowing the seeds into the next pits avoiding the opponent's kalah
     *
     * @param game The game to play
     * @param move The move to play
     * @return
     */
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
                log.debug("Avoiding other player Kalah");
                x++;
            }
            return gameConfiguration.getPits() == x ? 1 : x + 1;
        })
                .limit(seeds)
                .forEach(pit -> {
                    log.debug("Placing seed in pit "+pit);
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
