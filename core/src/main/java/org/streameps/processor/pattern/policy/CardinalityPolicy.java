package org.streameps.processor.pattern.policy;

/**
 * A cardinality policy is a semantic abstraction that controls how many matching
 * sets are created. The possible policies are single, unrestricted, and bounded.
 * 
 * @author Frank Appiah
 */
public class CardinalityPolicy implements PatternPolicy {

    @Override
    public boolean checkPolicy(Object... optional) {
        return false;
    }

    public PolicyType getPolicyType() {
        return PolicyType.CARDINALITY;
    }
}
