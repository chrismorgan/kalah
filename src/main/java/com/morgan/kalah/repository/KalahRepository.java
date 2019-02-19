package com.morgan.kalah.repository;

import com.morgan.kalah.model.Game;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class KalahRepository {

    private final Map<String, Game> gameRepository;
    private final AtomicLong gameCounter = new AtomicLong();

    public KalahRepository() {
        gameRepository = new ConcurrentHashMap<>();
    }

    public Game addGame(Game game) {
        String gameIdStr = getNewGameId();
        game.setId(gameIdStr);

        gameRepository.put(gameIdStr, game);
        return game;
    }

    public Optional<Game> getGame(String gameId) {
        return Optional.ofNullable(gameRepository.get(gameId));
    }

    private String getNewGameId() {
        Long gameId = gameCounter.incrementAndGet();
        return String.valueOf(gameId);

    }

}
