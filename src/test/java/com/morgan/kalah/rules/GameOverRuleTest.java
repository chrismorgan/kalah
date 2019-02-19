package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GameOverRuleTest {

    @Mock
    private GameConfiguration gameConfiguration;

    private GameOverRule gameOverRule;

    @Before
    public void setup() {
        given(gameConfiguration.getNorthPlayerKalah()).willReturn(14);
        given(gameConfiguration.getSouthPlayerKalah()).willReturn(7);
        gameOverRule = new GameOverRule(null, gameConfiguration);
    }

    @Test
    public void applyRule_gameNotOver_passthrough() {
        //Given
        Game game = new Game();
        game.setState(continuingState());
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(13);

        //When
        gameOverRule.applyRule(game, 8);

        //Then
        assertThat(game.getState().values()).containsSequence(6, 6, 6, 6, 6, 6, 0, 0, 7, 7, 7, 7, 7, 1);

    }

    @Test
    public void applyRule_gameOver_pitsEmptied() {
        //Given
        Game game = new Game();
        game.setState(finishedState());
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(7);

        //When
        gameOverRule.applyRule(game, 8);

        //Then
        assertThat(game.getState().values()).containsSequence(0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 47);

    }

    private Map<Integer, Integer> continuingState() {
        Map<Integer, Integer> state = new HashMap<>();
        state.put(1, 6);
        state.put(2, 6);
        state.put(3, 6);
        state.put(4, 6);
        state.put(5, 6);
        state.put(6, 6);
        state.put(7, 0);
        state.put(8, 0);
        state.put(9, 7);
        state.put(10, 7);
        state.put(11, 7);
        state.put(12, 7);
        state.put(13, 7);
        state.put(14, 1);
        return state;
    }

    private Map<Integer, Integer> finishedState() {
        Map<Integer, Integer> state = new HashMap<>();
        state.put(1, 0);
        state.put(2, 0);
        state.put(3, 0);
        state.put(4, 0);
        state.put(5, 0);
        state.put(6, 0);
        state.put(7, 15);
        state.put(8, 0);
        state.put(9, 7);
        state.put(10, 7);
        state.put(11, 7);
        state.put(12, 7);
        state.put(13, 7);
        state.put(14, 12);
        return state;
    }

}