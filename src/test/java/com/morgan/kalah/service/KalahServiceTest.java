package com.morgan.kalah.service;


import com.morgan.kalah.exception.BadInputException;
import com.morgan.kalah.exception.GameNotFoundException;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.GameDescriptor;
import com.morgan.kalah.model.GameState;
import com.morgan.kalah.repository.KalahRepository;
import com.morgan.kalah.rules.Rule;
import com.morgan.kalah.rules.RuleChain;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class KalahServiceTest {

    @org.junit.Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private KalahRepository kalahRepository;
    @Mock
    private ConversionService conversionService;
    @Mock
    private KalahGameFactory kalahGameFactory;
    @Mock
    private RuleChain ruleChain;
    @Mock
    private Rule rule;
    private Game game;
    private GameDescriptor gameDescriptor;
    private GameState gameState;
    private KalahService kalahService;

    @Before
    public void setup() {
        kalahService = new KalahService(kalahRepository, conversionService, kalahGameFactory, ruleChain);
        game = new Game();
        gameDescriptor = new GameDescriptor();
        gameState = new GameState();
        given(kalahGameFactory.createGame()).willReturn(game);
        given(kalahRepository.addGame(eq(game))).willReturn(game);

    }

    @Test
    public void createGame_baseCase_createsGame() {
        //Given
        game.setId("1");
        gameDescriptor.setId("1");
        gameDescriptor.setUri("http://localhost:8080/1");
        given(conversionService.convert(any(Game.class), (Class<GameDescriptor>) any(Class.class))).willReturn(gameDescriptor);

        //When
        ResponseEntity<GameDescriptor> gameDescriptorResponse = kalahService.createGame();

        //Then
        assertThat(gameDescriptorResponse.getStatusCodeValue()).isEqualTo(201);
        assertThat(gameDescriptorResponse.getBody()).isEqualTo(gameDescriptor);
        verify(kalahRepository).addGame(eq(game));
        verify(conversionService).convert(eq(game), any(Class.class));
        verify(kalahGameFactory).createGame();
    }

    @Test
    public void playMove_gameExists_playsMove() {
        //Given
        game.setId("1");
        gameState.setId("1");
        gameState.setUrl("http://localhost:8080/1");
        gameState.setState(new HashMap<>());
        given(kalahRepository.getGame(eq("1"))).willReturn(Optional.of(game));
        given(ruleChain.getRules()).willReturn(rule);
        given(rule.applyRule(any(Game.class), eq(8))).willReturn(game);
        given(conversionService.convert(any(Game.class), (Class<GameState>) any(Class.class))).willReturn(gameState);

        //When
        ResponseEntity<GameState> gameStateResponseEntity = kalahService.playMove("1", "8");

        //Then
        assertThat(gameStateResponseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(gameStateResponseEntity.getBody()).isEqualTo(gameState);
        verify(kalahRepository).getGame(eq("1"));
        verify(conversionService).convert(eq(game), any(Class.class));
        verify(ruleChain).getRules();
        verify(rule).applyRule(eq(game), eq(8));
    }

    @Test
    public void playMove_gameDoesNotExist_throwsException() {
        //Given
        game.setId("1");
        gameState.setId("1");
        gameState.setUrl("http://localhost:8080/1");
        gameState.setState(new HashMap<>());
        given(kalahRepository.getGame(eq("1"))).willThrow(new GameNotFoundException("1"));

        //Then
        expectedException.expect(GameNotFoundException.class);
        expectedException.expectMessage("Game 1 does not exist");

        //When
        ResponseEntity<GameState> gameStateResponseEntity = kalahService.playMove("1", "8");
    }

    @Test
    public void playMove_pitIsGibberish_throwsException() {
        //Given
        game.setId("1");
        gameState.setId("1");
        gameState.setUrl("http://localhost:8080/1");
        gameState.setState(new HashMap<>());
        given(kalahRepository.getGame(eq("1"))).willReturn(Optional.of(game));


        //Then
        expectedException.expect(BadInputException.class);
        expectedException.expectMessage("Input not valid: wibble");

        //When
        ResponseEntity<GameState> gameStateResponseEntity = kalahService.playMove("1", "wibble");
    }
}