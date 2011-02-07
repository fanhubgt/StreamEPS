package org.streameps.processor.pattern.policy;

public class CardinalityPolicy implements PatternPolicy {

    @Override
    public boolean checkPolicy(Object... optional) {

        return false;
    }

    public PolicyType getPolicyType() {
        return PolicyType.CARDINALITY;
    }
}
