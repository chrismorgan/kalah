package com.morgan.kalah.repository;

import com.morgan.kalah.model.Game;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class KalahRepository {

    private final Map<Long, Game> gameRepository;
    private final AtomicLong gameCounter = new AtomicLong();

    public KalahRepository() {
        gameRepository = new ConcurrentHashMap<>();
    }

    public Game addGame(Game game) {
        Long gameId = gameCounter.incrementAndGet();
        game.setId(gameId);

        gameRepository.put(gameId, game);
        return game;
    }

    public Optional<Game> getGame(String gameId) {
        return Optional.of(gameRepository.get(Long.valueOf(gameId)));
    }

    public Game update(Game game) {
        gameRepository.replace(game.getId(), game);
        return game;
    }


}
