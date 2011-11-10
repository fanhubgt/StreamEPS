package org.streameps.processor.pattern.policy;

import org.streameps.aggregation.collection.TreeMapCounter;
import org.streameps.core.MatchedEventSet;

/**
 * A consumption policy is a semantic abstraction that defines whether an event
 * instance is consumed as soon as it's included in a matching set, or whether
 * it can be included in subsequent matching sets. Possible consumption policies
 * are consume, reuse, and bounded reuse.
 * 
 * @author Frank Appiah
 */
public final class ConsumptionPolicy implements PatternPolicy {

    private ConsumptionType consumptionType = ConsumptionType.CONSUME;
    private MatchedEventSet matchingEventSet;
    private long boundCount = 0;
    private TreeMapCounter mapCounter;

    public ConsumptionPolicy(ConsumptionType consumptionType, MatchedEventSet matchingEventSet) {
        this.consumptionType = consumptionType;
        this.matchingEventSet = matchingEventSet;
        mapCounter = new TreeMapCounter();
    }

    public ConsumptionPolicy(ConsumptionType consumptionType, MatchedEventSet matchingEventSet, long count) {
        this.consumptionType = consumptionType;
        this.matchingEventSet = matchingEventSet;
        mapCounter = new TreeMapCounter();
        boundCount = count;
    }

    /**
     * 
     * @param optional list of optional values for the policy evaluation
     * @return success/failure indicator.
     */
    public boolean checkPolicy(Object... optional) {
        boolean result = false;
        switch (consumptionType) {
            case CONSUME:
                break;
            case BOUNDED_REUSE:
                long count = mapCounter.incrementAt(optional[0]/*An event instance*/);
                result = (count <= boundCount);
                break;
            case REUSE:
                break;
        }
        return result;
    }

    public MatchedEventSet getMatchingEventSet() {
        return matchingEventSet;
    }

    /**
     * It sets the bounded value for the bounded reuse consumption policy.
     * 
     * @param boundCount bounded value for policy.
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
