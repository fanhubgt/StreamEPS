package org.streameps.processor.pattern.policy;

import org.streameps.core.MatchedEventSet;

public class ConsumptionPolicy implements PatternPolicy {

    private ConsumptionType consumptionType;
    private MatchedEventSet matchingEventSet;
    private long boundCount = 0;

    public ConsumptionPolicy(ConsumptionType consumptionType, MatchedEventSet matchingEventSet) {
        this.consumptionType = consumptionType;
        this.matchingEventSet = matchingEventSet;
    }

    public boolean checkPolicy(Object... optional) {
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

    public MatchedEventSet getMatchingEventSet() {
        return matchingEventSet;
    }

    /**
     * It sets the bounded value for the bounded reuse consumption policy.
     * 
     * @param boundCount
     */
    public void setBoundCount(long boundCount) {
        this.boundCount = boundCount;
    }

    /**
     * It returns the bounded value for the bounded reuse consumption policy.
     */
    public long getBoundCount() {
        return boundCount;
    }

    public PolicyType getPolicyType() {
        return PolicyType.CONSUMPTION;
    }
}
