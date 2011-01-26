package org.streameps.processor.pattern.policy;

import org.streameps.core.MatchingEventSet;

public class ConsumptionPolicy implements PatternPolicy {

    private ConsumptionType consumptionType;
    private MatchingEventSet matchingEventSet;

    public ConsumptionPolicy(ConsumptionType consumptionType, MatchingEventSet matchingEventSet) {
        this.consumptionType = consumptionType;
        this.matchingEventSet = matchingEventSet;
    }

    public boolean checkPolicy() {
        switch (consumptionType) {
            case CONSUME:
                break;
            case BOUNDED_REUSE:
                break;
            case REUSE:
                break;
        }
        return false;
    }

    public MatchingEventSet getMatchingEventSet() {
        return matchingEventSet;
    }
    
}
