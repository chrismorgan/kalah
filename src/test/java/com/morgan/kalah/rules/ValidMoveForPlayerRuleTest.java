package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.exception.InvalidMoveForPlayerException;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ValidMoveForPlayerRuleTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Mock
    private GameConfiguration gameConfiguration;
    private ValidMoveForPlayerRule validMoveForPlayerRule;

    @Before
    public void setup() {
        validMoveForPlayerRule = new ValidMoveForPlayerRule(null, gameConfiguration);
        given(gameConfiguration.getPits()).willReturn(14);
        given(gameConfiguration.getNorthPlayerKalah()).willReturn(14);
        given(gameConfiguration.getSouthPlayerKalah()).willReturn(7);
    }

    @Test
    public void applyRule_northActivePlaysPitInRange_moveAllowed() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.NORTH);
        game.setLastPitPlayed(0);
        game.setState(startingState());

        //When
        validMoveForPlayerRule.applyRule(game, 8);

        //Then
    }

    @Test
    public void applyRule_northActivePlaysKalahPitInRange_exceptionThrown() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.NORTH);
        game.setLastPitPlayed(0);
        game.setState(startingState());

        //Then
        expectedException.expect(InvalidMoveForPlayerException.class);

        //When
        validMoveForPlayerRule.applyRule(game, 14);
    }

    @Test
    public void applyRule_northActivePlaysPitBelowRange_exceptionThrown() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.NORTH);
        game.setLastPitPlayed(0);
        game.setState(startingState());

        //Then
        expectedException.expect(InvalidMoveForPlayerException.class);

        //When
        validMoveForPlayerRule.applyRule(game, 0);

    }

    @Test
    public void applyRule_northActivePlaysPitOverRange_exceptionThrown() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.NORTH);
        game.setLastPitPlayed(0);
        game.setState(startingState());

        //Then
        expectedException.expect(InvalidMoveForPlayerException.class);

        //When
        validMoveForPlayerRule.applyRule(game, 15);

    }

    @Test
    public void applyRule_southActivePlaysPitInRange_moveAllowed() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(0);
        game.setState(startingState());

        //When
        validMoveForPlayerRule.applyRule(game, 3);

        //Then

    }

    @Test
    public void applyRule_southActivePlaysPitBelowRange_exceptionThrown() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(0);
        game.setState(startingState());

        //Then
        expectedException.expect(InvalidMoveForPlayerException.class);

        //When
        validMoveForPlayerRule.applyRule(game, 0);

    }

    @Test
    public void applyRule_southActivePlaysPitOverRange_exceptionThrown() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(0);
        game.setState(startingState());

        //Then
        expectedException.expect(InvalidMoveForPlayerException.class);

        //When
        validMoveForPlayerRule.applyRule(game, 15);

    }

    @Test
    public void applyRule_southActivePlaysKalahPitInRange_exceptionThrown() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(0);
        game.setState(startingState());

        //Then
        expectedException.expect(InvalidMoveForPlayerException.class);

        //When
        validMoveForPlayerRule.applyRule(game, 7);
    }


    private Map<Integer, Integer> startingState() {
        Map<Integer, Integer> state = new HashMap<>();
        state.put(1, 6);
        state.put(2, 6);
        state.put(3, 6);
        state.put(4, 6);
        state.put(5, 6);
        state.put(6, 6);
        state.put(7, 0);
        state.put(8, 6);
        state.put(9, 6);
        state.put(10, 6);
        state.put(11, 6);
        state.put(12, 6);
        state.put(13, 6);
        state.put(14, 0);
        return state;
    }
}