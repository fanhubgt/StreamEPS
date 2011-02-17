package org.streameps.processor.pattern.policy;

import org.streameps.processor.pattern.BasePattern;

/**
 * The pattern is tested for each time a new event is added to the participant
 * event set. The default evaluation policy is to defer outputting matched
 * event immediately.
 * 
 * @author Frank Appiah
 */
public class EvaluationPolicy implements PatternPolicy {

    private EvaluationPolicyType type = EvaluationPolicyType.DEFERRED;
    private BasePattern basePattern;

    public EvaluationPolicy(BasePattern basePattern) {
        this.basePattern = basePattern;
    }

    /**
     * @param type the type to set
     */
    public void setType(EvaluationPolicyType type) {
        this.type = type;
    }

    @Override
    public boolean checkPolicy(Object... optional) {
        switch (type) {
            case DEFERRED:
                return true;
            case IMMEDIATE:
                basePattern.output();
                return true;
        }
        return false;
    }

    public PolicyType getPolicyType() {
        return PolicyType.EVALUATION;
    }
    
}
