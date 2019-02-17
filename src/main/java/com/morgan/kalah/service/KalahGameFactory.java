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

    public Game newGame() {
        Game game = new Game();
        game.setActivePlayer(Player.ONE);
        Map<Integer, Integer> state = IntStream.rangeClosed(1, gameConfiguration.getPitsize())
                .boxed()
                .collect(Collectors.toMap(identity(), integer -> 0));
        game.setState(state);
        return game;
    }
}
