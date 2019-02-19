package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Checks if the move results in a capture and alters the state accordingly
 */
@Slf4j
public class CapturedSeedsRule implements Rule {

    private final GameConfiguration gameConfiguration;

    private final Rule nextRule;

    @Autowired
    CapturedSeedsRule(Rule nextRule, GameConfiguration gameConfiguration) {
        this.nextRule = nextRule;
        this.gameConfiguration = gameConfiguration;
    }

    /**
     * Checks if the last move results in a capture because the last seeds ends in the empty pit of the active player
     * This captures the opposite pit seeds into this player's house.
     *
     * @param game The game to play
     * @param move The move to play
     * @return The game after the capture has been perfromed
     */
    @Override
    public Game applyRule(Game game, int move) {

        if (isLastPitACapture(game)) {
            captureToKalah(game);
        }

        if (nextRule != null) {
            return nextRule.applyRule(game, move);
        } else {
            return game;
        }
    }

    private void captureToKalah(Game game) {
        int lastPlayedPit = game.getLastPitPlayed();
        Map<Integer, Integer> state = game.getState();

        int myPitSeeds = state.get(lastPlayedPit);
        state.put(lastPlayedPit, 0);
        int oppositePit = oppositePit(lastPlayedPit);
        int theirPitSeeds = state.get(oppositePit);
        state.put(oppositePit, 0);

        log.debug("Capturing " + theirPitSeeds + " from " + oppositePit + " to my kalah");
        if (Player.NORTH == game.getActivePlayer()) {
            int northKalah = gameConfiguration.getNorthPlayerKalah();
            int currentKalah = state.get(northKalah);

            state.put(northKalah, currentKalah + myPitSeeds + theirPitSeeds);
        } else {
            int southKalah = gameConfiguration.getSouthPlayerKalah();
            int currentKalah = state.get(southKalah);
            state.put(southKalah, currentKalah + myPitSeeds + theirPitSeeds);
        }
    }

    private boolean isLastPitACapture(Game game) {
        int lastPlayedPit = game.getLastPitPlayed();

        if (!playedInOwnPit(lastPlayedPit, game.getActivePlayer())) {
            log.debug("Not finished move in own pits, not capture");
            return false;
        }

        if (game.getState().get(lastPlayedPit) == 1) {
            log.debug("Finished move in own pit with one seed, capture");
            return true;
        } else {
            log.debug("Finished move in own pit with more than one seed, not capture");
            return false;
        }
    }

    private int oppositePit(int pitId) {
        int totalPits = gameConfiguration.getPits();
        return totalPits - pitId;
    }

    private boolean playedInOwnPit(int lastPlayed, Player player) {
        log.debug("Player " + player + " played last move in " + lastPlayed);
        if (lastPlayed == gameConfiguration.getNorthPlayerKalah() |
                lastPlayed == gameConfiguration.getSouthPlayerKalah()) {
            return false;
        }

        if (Player.NORTH == player) {
            if (lastPlayed < gameConfiguration.getNorthPlayerKalah() && lastPlayed > gameConfiguration.getSouthPlayerKalah()) {

                return true;
            }
        } else {
            if (lastPlayed > 0 && lastPlayed < gameConfiguration.getSouthPlayerKalah()) {
                return true;
            }
        }
        return false;
    }

}
