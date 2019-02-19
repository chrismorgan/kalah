package com.morgan.kalah.repository;

import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


public class KalahRepositoryTest {

    private KalahRepository kalahRepository;

    @Before
    public void setup() {
        kalahRepository = new KalahRepository();
    }

    @Test
    public void addGame_noExistingGames_createsGame() {
        //Given
        Game game = new Game();
        game.setState(new HashMap<>());
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(0);

        //When
        kalahRepository.addGame(game);

        //Then
        assertThat(kalahRepository.getGame(game.getId())).isPresent();

    }

    @Test
    public void addGame_oneExistingGames_createsGameWithNextNumber() {
        //Given
        Game game = new Game();
        game.setState(new HashMap<>());
        game.setActivePlayer(Player.SOUTH);
        game.setLastPitPlayed(0);

        Game game2 = new Game();
        game2.setState(new HashMap<>());
        game2.setActivePlayer(Player.SOUTH);
        game2.setLastPitPlayed(0);

        //When
        kalahRepository.addGame(game);
        kalahRepository.addGame(game2);

        //Then
        String id1 = kalahRepository.getGame(game.getId()).get().getId();
        assertThat(game2.getId()).isEqualTo(String.valueOf(Integer.valueOf(id1) + 1));
    }
}