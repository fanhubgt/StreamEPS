package org.streameps.processor.pattern.policy;

public interface PatternPolicy {

    public boolean checkPolicy(Object... optional);

    public PolicyType getPolicyType();
}
