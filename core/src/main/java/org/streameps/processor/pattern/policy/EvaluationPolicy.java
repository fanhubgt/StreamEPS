package org.streameps.processor.pattern.policy;

import org.streameps.processor.pattern.BasePattern;

/**
 * The pattern is tested for each time a new event is added to the participant
 * event set.
 * @author Development Team
 */
public class EvaluationPolicy implements PatternPolicy {

    private EvaluationPolicyType type;
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
                break;
            case IMMEDIATE:
                basePattern.output();
                break;
        }
        return false;
    }

    public PolicyType getPolicyType() {
        return PolicyType.EVALUATION;
    }
}
