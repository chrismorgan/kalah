package com.morgan.kalah.rules;

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
public class NextPlayerRuleTest {

    @Mock
    private GameConfiguration gameConfiguration;

    private NextPlayerRule nextPlayerRule;

    @Before
    public void setup() {
        given(gameConfiguration.getNorthPlayerKalah()).willReturn(14);
        given(gameConfiguration.getSouthPlayerKalah()).willReturn(7);
        nextPlayerRule = new NextPlayerRule(null, gameConfiguration);
    }

    @Test
    public void applyRule_currentPlayerNorthBasicState_nextPlayerSouth() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.NORTH);
        game.setLastPitPlayed(0);

        //When
        nextPlayerRule.applyRule(game, 8);

        //Then
        assertThat(game.getActivePlayer()).isEqualTo(Player.SOUTH);
    }

    @Test
    public void applyRule_currentPlayerNorthLandedInKalah_nextPlayerNorth() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.NORTH);
        game.setLastPitPlayed(14);

        //When
        nextPlayerRule.applyRule(game, 8);

        //Then
        assertThat(game.getActivePlayer()).isEqualTo(Player.NORTH);
    }

    @Test
    public void applyRule_currentPlayerSouithBasicState_nextPlayerNorth() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(0);

        //When
        nextPlayerRule.applyRule(game, 8);

        //Then
        assertThat(game.getActivePlayer()).isEqualTo(Player.NORTH);
    }

    @Test
    public void applyRule_currentPlayerSouthLandedInKalah_nextPlayerSouth() {
        //Given
        Game game = new Game();
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(7);

        //When
        nextPlayerRule.applyRule(game, 8);

        //Then
        assertThat(game.getActivePlayer()).isEqualTo(Player.SOUTH);
    }
}