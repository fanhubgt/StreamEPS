package org.streameps.processor.pattern.policy;

/**
 * 
 * @author Frank Appiah
 */
public class RepeatedTypePolicy implements PatternPolicy {

    private RepeatedType repeatedType;
    
    @Override
    public boolean checkPolicy(Object... optional) {
        return false;
    }

    public PolicyType getPolicyType() {
        return PolicyType.REPEATED;
    }

    public void setRepeatedType(RepeatedType repeatedType) {
        this.repeatedType = repeatedType;
    }
    
}
