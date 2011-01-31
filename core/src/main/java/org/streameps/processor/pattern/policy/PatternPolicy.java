package org.streameps.processor.pattern.policy;

public interface PatternPolicy {

    public boolean checkPolicy();

    public PolicyType getPolicyType();
}
