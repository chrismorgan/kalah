package com.morgan.kalah.rules;


import com.morgan.kalah.exception.EmptyPitException;
import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.HashMap;
import java.util.Map;

@RunWith(JUnit4.class)
public class EmptyPitRuleTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private Game game;
    private EmptyPitRule emptyPitRule = new EmptyPitRule(null);

    @Test
    public void emptyPit_playsNonEmptyPit_passesThrough() {
        //Given
        game = new Game();
        game.setActivePlayer(Player.NORTH);
        game.setState(startingState());
        game.setLastPitPlayed(0);


        //When
        emptyPitRule.applyRule(game, 10);

        //Then
        //Nothing
    }

    @Test
    public void emptyPit_playsEmptyPit_throwsException() {
        //Given
        game = new Game();
        game.setActivePlayer(Player.NORTH);
        game.setState(startingStateEmptyPits());
        game.setLastPitPlayed(0);

        //Then
        expectedException.expect(EmptyPitException.class);

        //When
        emptyPitRule.applyRule(game, 11);


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

    private Map<Integer, Integer> startingStateEmptyPits() {
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
        state.put(11, 0);
        state.put(12, 6);
        state.put(13, 6);
        state.put(14, 0);
        return state;
    }
}