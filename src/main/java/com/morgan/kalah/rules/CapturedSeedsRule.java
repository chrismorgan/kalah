package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class CapturedSeedsRule implements Rule {

    private final GameConfiguration gameConfiguration;

    private final Rule nextRule;

    @Autowired
    CapturedSeedsRule(Rule nextRule, GameConfiguration gameConfiguration) {
        this.nextRule = nextRule;
        this.gameConfiguration = gameConfiguration;
    }

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

        int myPit = state.get(lastPlayedPit);
        state.put(lastPlayedPit, 0);
        int theirPit = state.get(oppositePit(lastPlayedPit));
        state.put(oppositePit(lastPlayedPit), 0);

        if (Player.NORTH == game.getActivePlayer()) {
            int northKalah = gameConfiguration.getNorthPlayerKalah();
            int currentKalah = state.get(northKalah);
            state.put(northKalah, currentKalah + myPit + theirPit);
        } else {
            int southKalah = gameConfiguration.getSouthPlayerKalah();
            int currentKalah = state.get(southKalah);
            state.put(southKalah, currentKalah + myPit + theirPit);
        }
    }

    private boolean isLastPitACapture(Game game) {
        int lastPlayedPit = game.getLastPitPlayed();

        if (lastPlayedPit == gameConfiguration.getNorthPlayerKalah() ||
                lastPlayedPit == gameConfiguration.getSouthPlayerKalah()) {
            return false;
        }

        if (game.getState().get(lastPlayedPit) == 1) {
            return true;
        } else {
            return false;
        }
    }

    private int oppositePit(int pitId) {
        int totalPits = gameConfiguration.getPits();
        return totalPits - pitId;
    }
}
