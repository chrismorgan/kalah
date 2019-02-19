package com.morgan.kalah.service;

import com.morgan.kalah.api.GamesApiDelegate;
import com.morgan.kalah.exception.BadInputException;
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

/**
 * Delegate to perform the controller actions
 * - createGame
 * - playMove
 */
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

    /**
     * Create a new game
     * Create a game and stores it in a repository where it can be retrieved to play moves
     *
     * @return a new initialised game representation
     */
    @Override
    public ResponseEntity<GameDescriptor> createGame() {
        Game game = kalahRepository.addGame(kalahGameFactory.createGame());
        GameDescriptor gameDescriptor = conversionService.convert(game, GameDescriptor.class);
        return new ResponseEntity<>(gameDescriptor, HttpStatus.CREATED);
    }

    /**
     * Play a move on a given game
     *
     * @param gameId The game to play
     * @param pitId  The id of the pit to play as the move
     * @return a representation of the game after the move has been played
     * @throws GameNotFoundException                                    if the game number does not exist
     * @throws com.morgan.kalah.exception.InvalidMoveForPlayerException if the move number is out of the range allowed
     * @throws com.morgan.kalah.exception.EmptyPitException             if the move would be on an empty pit
     */
    @Override
    public ResponseEntity<GameState> playMove(String gameId, String pitId) {
        Game game = kalahRepository.getGame(gameId).orElseThrow(() -> new GameNotFoundException(gameId));

        try {
            game = ruleChain.getRules().applyRule(game, Integer.valueOf(pitId));
        } catch (NumberFormatException nfe) {
            throw new BadInputException(pitId, nfe);
        }

        GameState gameState = conversionService.convert(game, GameState.class);
        return new ResponseEntity<>(gameState, HttpStatus.OK);
    }
}
