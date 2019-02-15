package com.morgan.kalah.service;

import com.morgan.kalah.api.GamesApiDelegate;
import com.morgan.kalah.exception.GameNotFoundException;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.GameDescriptor;
import com.morgan.kalah.model.GameState;
import com.morgan.kalah.repository.KalahRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class KalahService implements GamesApiDelegate {

    private final KalahRepository kalahRepository;

    @Autowired
    public KalahService(KalahRepository kalahRepository){
        this.kalahRepository= kalahRepository;
    }

    @Override
    public ResponseEntity<GameDescriptor> createGame(){
        Game game = kalahRepository.addGame(new Game());
        GameDescriptor gameDescriptor = new GameDescriptor()
                .id(String.valueOf(game.getId()))
                .uri("https://localhost:8080/" + game.getId());
        return new ResponseEntity<>(gameDescriptor, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GameState> playMove(String gameId, String pitId) {
        Game game = kalahRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
        GameState gameState = new GameState();
        gameState.setId(String.valueOf(game.getId()));
        gameState.setState(new HashMap<>());
        gameState.setUrl("https://localhost:8080/" + gameId);
        return new ResponseEntity<>(gameState, HttpStatus.OK);
    }
}
