package com.morgan.kalah.service;

import com.morgan.kalah.api.GamesApiDelegate;
import com.morgan.kalah.exception.GameNotFoundException;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.GameDescriptor;
import com.morgan.kalah.model.GameState;
import com.morgan.kalah.repository.KalahRepository;
import com.morgan.kalah.rules.RuleChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class KalahService implements GamesApiDelegate {

    private final KalahRepository kalahRepository;

    private final ConversionService conversionService;

    private final KalahGameFactory kalahGameFactory;

    private final RuleChain ruleChain;

    @Autowired
    public KalahService(KalahRepository kalahRepository,
                        ConversionService conversionService,
                        KalahGameFactory kalahGameFactory,
                        RuleChain ruleChain) {
        this.kalahRepository = kalahRepository;
        this.conversionService = conversionService;
        this.kalahGameFactory = kalahGameFactory;
        this.ruleChain = ruleChain;
    }

    @Override
    public ResponseEntity<GameDescriptor> createGame(){
        Game game = kalahRepository.addGame(kalahGameFactory.createGame());
        GameDescriptor gameDescriptor = conversionService.convert(game, GameDescriptor.class);
        return new ResponseEntity<>(gameDescriptor, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<GameState> playMove(String gameId, String pitId) {
        Game game = kalahRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

        game = ruleChain.getRules().applyRule(game, Integer.valueOf(pitId));

        GameState gameState = conversionService.convert(game, GameState.class);
        return new ResponseEntity<>(gameState, HttpStatus.OK);
    }
}
