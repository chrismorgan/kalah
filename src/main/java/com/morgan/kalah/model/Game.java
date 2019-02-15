package com.morgan.kalah.model;

import lombok.Data;

import java.util.Map;

@Data
public class Game {

    private long id;
    private int activePlayer;
    private Map<Integer,Integer> state;
}
