package com.morgan.kalah.service;

import com.morgan.kalah.configuration.GameConfiguration;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(MockitoJUnitRunner.class)
public class KalahGameFactoryTest {

    @Mock
    private GameConfiguration gameConfiguration;

    private KalahGameFactory kalahGameFactory;

    @Before
    public void setup() {
        kalahGameFactory = new KalahGameFactory(gameConfiguration);
    }

    @Test
    public void createGame_createStartingGame_withStartingState() {
        //Given
        given(gameConfiguration.getPits()).willReturn(14);
        given(gameConfiguration.getNorthPlayerKalah()).willReturn(14);
        given(gameConfiguration.getSouthPlayerKalah()).willReturn(7);
        given(gameConfiguration.getSeeds()).willReturn(6);

        //When
        Game game = kalahGameFactory.createGame();

        //Then
        assertThat(game.getActivePlayer()).isEqualTo(Player.NORTH);
        assertThat(game.getLastPitPlayed()).isEqualTo(0);
        assertThat(game.getState()).hasSize(14);
        assertThat(game.getState()).containsKeys(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);
        assertThat(game.getState()).containsValues(6, 6, 6, 6, 6, 6, 0, 6, 6, 6, 6, 6, 6, 0);
    }
}