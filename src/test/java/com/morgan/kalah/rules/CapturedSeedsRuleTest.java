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
public class CapturedSeedsRuleTest {

    @Mock
    private GameConfiguration gameConfiguration;

    private CapturedSeedsRule capturedSeedsRule;

    @Before
    public void setup() {
        capturedSeedsRule = new CapturedSeedsRule(null, gameConfiguration);
        given(gameConfiguration.getPits()).willReturn(14);
        given(gameConfiguration.getNorthPlayerKalah()).willReturn(14);
        given(gameConfiguration.getSouthPlayerKalah()).willReturn(7);
        given(gameConfiguration.getSeeds()).willReturn(6);
    }

    @Test
    public void applyRule_nonCapturingMove_noCapture() {
        //Given
        Game game = new Game();
        game.setState(startingState());
        game.setActivePlayer(Player.NORTH);
        game.setLastPitPlayed(14);

        //When
        capturedSeedsRule.applyRule(game, 8);

        //Then
        assertThat(game.getState()).containsValues(6, 6, 6, 6, 6, 6, 0, 0, 7, 7, 7, 7, 7, 1);

    }

    @Test
    public void applyRule_capturingMove_captureToKalah() {
        //Given
        Game game = new Game();
        game.setState(startingStateCapture());
        game.setActivePlayer(Player.NORTH);
        game.setLastPitPlayed(12);

        //When
        capturedSeedsRule.applyRule(game, 8);

        //Then
        assertThat(game.getState()).containsValues(6, 0, 6, 6, 6, 6, 0, 0, 7, 7, 7, 0, 7, 8);

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
        state.put(8, 0);
        state.put(9, 7);
        state.put(10, 7);
        state.put(11, 7);
        state.put(12, 7);
        state.put(13, 7);
        state.put(14, 1);
        return state;
    }

    private Map<Integer, Integer> startingStateCapture() {
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
        state.put(12, 1);
        state.put(13, 7);
        state.put(14, 1);
        return state;
    }

}