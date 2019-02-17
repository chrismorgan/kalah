package com.morgan.kalah.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Game {

    private String id;
    private Player activePlayer = Player.NORTH;
    private Map<Integer, Integer> state = new HashMap<>();
}
