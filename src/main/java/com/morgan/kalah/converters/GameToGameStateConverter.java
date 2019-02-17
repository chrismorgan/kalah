package com.morgan.kalah.converters;

import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.GameState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Component
public class GameToGameStateConverter implements Converter<Game, GameState> {

    @Value("${game.host}")
    private String host;

    @Value("${game.port}")
    private String port;

    @Override
    public GameState convert(Game game) {
        GameState gameState = new GameState();
        gameState.setId(game.getId());

        UriComponents uri = UriComponentsBuilder.newInstance()
                .host(host)
                .port(port)
                .scheme("http")
                .pathSegment("games", game.getId())
                .build();
        gameState.setUrl(uri.toUriString());

        Map<String, String> stateMap = new HashMap<>();
        game.getState().forEach((key, value) -> {
            stateMap.put(String.valueOf(key), String.valueOf(value));
        });
        gameState.setState(Collections.unmodifiableMap(stateMap));

        return gameState;
    }
}
