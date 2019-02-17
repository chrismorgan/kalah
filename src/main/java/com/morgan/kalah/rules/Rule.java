package com.morgan.kalah.rules;

import com.morgan.kalah.model.Game;

public interface Rule {

    Game applyRule(Game game, int move);

}
