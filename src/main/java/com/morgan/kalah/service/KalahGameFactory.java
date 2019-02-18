package com.morgan.kalah.service;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.function.Function.identity;

@Service
public class KalahGameFactory {

    private final GameConfiguration gameConfiguration;

    @Autowired
    public KalahGameFactory(GameConfiguration gameConfiguration) {
        this.gameConfiguration = gameConfiguration;
    }

    public Game createGame() {
        Game game = new Game();
        game.setActivePlayer(Player.NORTH);
        Map<Integer, Integer> state = IntStream.rangeClosed(1, gameConfiguration.getPits())
                .boxed()
                .collect(Collectors.toMap(identity(), this::getStonesForPit));
        game.setState(state);
        game.setLastPitPlayed(0);
        return game;
    }

    private Integer getStonesForPit(int pitId) {
        if (pitId == gameConfiguration.getNorthPlayerKalah() || pitId == gameConfiguration.getSouthPlayerKalah()) {
            return 0;
        } else {
            return gameConfiguration.getSeeds();
        }
    }
}
