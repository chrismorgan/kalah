package com.morgan.kalah.converters;

import com.morgan.kalah.model.Game;
import com.morgan.kalah.model.GameDescriptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GameToGameDescriptorConverter implements Converter<Game, GameDescriptor> {

    @Value("${game.host}")
    private String host;

    @Value("${game.port}")
    private String port;

    @Override
    public GameDescriptor convert(Game game) {

        GameDescriptor gameDescriptor = new GameDescriptor();
        gameDescriptor.setId(game.getId());

        UriComponents uri = UriComponentsBuilder.newInstance()
                .host(host)
                .port(port)
                .scheme("http")
                .pathSegment("games", game.getId())
                .build();
        gameDescriptor.setUri(uri.toUriString());

        return gameDescriptor;
    }
}
