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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MoveSeedsRuleTest {

    private final Map<Integer, Integer> state = new HashMap<>();
    @Mock
    private GameConfiguration gameConfiguration;
    private Game game = new Game();
    private MoveSeedsRule moveSeedsRule;

    @Before
    public void setup() {
        moveSeedsRule = new MoveSeedsRule(null, gameConfiguration);
        when(gameConfiguration.getPits()).thenReturn(14);
        when(gameConfiguration.getNorthPlayerKalah()).thenReturn(14);
        when(gameConfiguration.getSouthPlayerKalah()).thenReturn(7);

    }

    @Test
    public void applyRule_northPlays8_placesInKalah() {
        //Given
        startingState();
        game.setState(state);
        game.setActivePlayer(Player.NORTH);
        game.setLastPitPlayed(0);

        //When
        moveSeedsRule.applyRule(game, 8);

        //Then
        assertThat(game.getState().get(8)).isEqualTo(0);
        assertThat(game.getState().get(9)).isEqualTo(7);
        assertThat(game.getState().get(10)).isEqualTo(7);
        assertThat(game.getState().get(11)).isEqualTo(7);
        assertThat(game.getState().get(12)).isEqualTo(7);
        assertThat(game.getState().get(13)).isEqualTo(7);
        assertThat(game.getState().get(14)).isEqualTo(1);
        assertThat(game.getState().get(1)).isEqualTo(6);
    }

    @Test
    public void applyRule_northPlaysIntoSouthKalah_noSeedPlacedInSouthKalah() {
        //Given
        avoidKalahStateNorth();
        game.setState(state);
        game.setActivePlayer(Player.NORTH);
        game.setLastPitPlayed(0);

        //When
        moveSeedsRule.applyRule(game, 13);

        //Then
        assertThat(game.getState().get(13)).isEqualTo(0);
        assertThat(game.getState().get(14)).isEqualTo(2);
        assertThat(game.getState().get(1)).isEqualTo(7);
        assertThat(game.getState().get(2)).isEqualTo(7);
        assertThat(game.getState().get(3)).isEqualTo(7);
        assertThat(game.getState().get(4)).isEqualTo(6);
        assertThat(game.getState().get(5)).isEqualTo(7);
        assertThat(game.getState().get(6)).isEqualTo(7);
        assertThat(game.getState().get(7)).isEqualTo(0);
        assertThat(game.getState().get(8)).isEqualTo(1);
    }

    @Test
    public void applyRule_southPlaysIntoNorthKalah_noSeedPlacedInNorthKalah() {
        //Given
        avoidKalahStateSouth();
        game.setState(state);
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(0);

        //When
        moveSeedsRule.applyRule(game, 6);

        //Then
        assertThat(game.getState().get(6)).isEqualTo(0);
        assertThat(game.getState().get(7)).isEqualTo(1);
        assertThat(game.getState().get(8)).isEqualTo(7);
        assertThat(game.getState().get(9)).isEqualTo(8);
        assertThat(game.getState().get(10)).isEqualTo(8);
        assertThat(game.getState().get(11)).isEqualTo(8);
        assertThat(game.getState().get(12)).isEqualTo(8);
        assertThat(game.getState().get(13)).isEqualTo(8);
        assertThat(game.getState().get(14)).isEqualTo(0);
        assertThat(game.getState().get(1)).isEqualTo(7);
    }

    private void startingState() {
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
    }

    private void avoidKalahStateNorth() {
        state.put(1, 6);
        state.put(2, 6);
        state.put(3, 6);
        state.put(4, 5);
        state.put(5, 6);
        state.put(6, 6);
        state.put(7, 0);
        state.put(8, 0);
        state.put(9, 7);
        state.put(10, 7);
        state.put(11, 7);
        state.put(12, 7);
        state.put(13, 8);
        state.put(14, 1);
    }

    private void avoidKalahStateSouth() {
        state.put(1, 6);
        state.put(2, 6);
        state.put(3, 6);
        state.put(4, 5);
        state.put(5, 6);
        state.put(6, 8);
        state.put(7, 0);
        state.put(8, 6);
        state.put(9, 7);
        state.put(10, 7);
        state.put(11, 7);
        state.put(12, 7);
        state.put(13, 7);
        state.put(14, 0);
    }
}