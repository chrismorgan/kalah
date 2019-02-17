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
        game.setActivePlayer(Player.ONE);
        Map<Integer, Integer> state = IntStream.rangeClosed(1, gameConfiguration.getPits())
                .boxed()
                .collect(Collectors.toMap(identity(), integer -> getStonesForPit(integer)));
        game.setState(state);
        return game;
    }

    private Integer getStonesForPit(int pitId) {
        if (pitId == gameConfiguration.getPlayer1Kalah() || pitId == gameConfiguration.getPlayer2Kalah()) {
            return 0;
        } else {
            return gameConfiguration.getStones();
        }
    }
}
