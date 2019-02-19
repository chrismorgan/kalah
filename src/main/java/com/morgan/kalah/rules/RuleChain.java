package com.morgan.kalah.rules;

import com.morgan.kalah.configuration.GameConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Rule chain to apply each game rule, chained to the next rule
 */
@Component
public class RuleChain {

    @Autowired
    private GameConfiguration gameConfiguration;

    @Bean
    public Rule getRules() {
        return validMoveRule();
    }

    @Bean
    public Rule validMoveRule() {
        return new ValidMoveForPlayerRule(emptyPitRule(), gameConfiguration);
    }

    @Bean
    public Rule emptyPitRule() {
        return new EmptyPitRule(moveSeedsRule());
    }

    @Bean
    public Rule moveSeedsRule() {
        return new MoveSeedsRule(captureSeedsRule(), gameConfiguration);
    }

    @Bean
    public Rule captureSeedsRule() {
        return new CapturedSeedsRule(nextPlayerRule(), gameConfiguration);
    }

    @Bean
    public Rule nextPlayerRule() {
        return new NextPlayerRule(gameOverRule(), gameConfiguration);
    }

    @Bean
    public Rule gameOverRule() {
        return new GameOverRule(null, gameConfiguration);
    }
}
