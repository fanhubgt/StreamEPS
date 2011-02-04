package org.streameps.processor.pattern.policy;

public class CardinalityPolicy implements PatternPolicy {

    @Override
    public boolean checkPolicy() {

        return false;
    }

    public PolicyType getPolicyType() {
        return PolicyType.CARDINALITY;
    }
}