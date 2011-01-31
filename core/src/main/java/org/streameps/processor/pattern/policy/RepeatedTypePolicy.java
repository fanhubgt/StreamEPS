package org.streameps.processor.pattern.policy;

public class RepeatedTypePolicy implements PatternPolicy {

    private RepeatedType repeatedType;
    
    @Override
    public boolean checkPolicy() {
        return false;
    }

    public PolicyType getPolicyType() {
        return PolicyType.REPEATED;
    }

    public void setRepeatedType(RepeatedType repeatedType) {
        this.repeatedType = repeatedType;
    }
    
}
