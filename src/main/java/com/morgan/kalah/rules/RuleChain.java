package com.morgan.kalah.rules;

import org.springframework.stereotype.Component;

@Component
public class RuleChain {

    public Rule getRules() {
        return validMoveRule();
    }

    private Rule validMoveRule() {
        return new ValidMoveForPlayerRule(emptyPitRule());
    }

    private Rule emptyPitRule() {
        return new EmptyPitRule(moveSeedsRule());
    }

    private Rule moveSeedsRule() {
        return new MoveSeedsRule(null);
    }
}
