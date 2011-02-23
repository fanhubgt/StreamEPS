package org.streameps.processor.pattern.policy;

import org.streameps.processor.pattern.BasePattern;

/**
 * An evaluation policy is a semantic abstraction that determines when the matching
 * process is to be evaluated. The default evaluation policy is to defer outputting
 * matched event immediately.
 * 
 * @author Frank Appiah
 * @version 0.2.2
 */
public final class EvaluationPolicy implements PatternPolicy {

    private EvaluationPolicyType type = EvaluationPolicyType.DEFERRED;
    private BasePattern basePattern;

    public EvaluationPolicy(BasePattern basePattern) {
        this.basePattern = basePattern;
    }

    public EvaluationPolicy(BasePattern basePattern, EvaluationPolicyType policyType) {
        this.basePattern = basePattern;
        this.type = policyType;
    }

    public EvaluationPolicy(BasePattern basePattern, String policyType) {
        this.basePattern = basePattern;
        setTypeAsString(policyType);
    }

    /**
     * It sets the type of evaluation as a string value.
     * Supported values: <b>immediate</b>, <b>deferred</b>.
     * @param typeStr type of evaluation value.
     */
    public void setTypeAsString(String typeStr) {
        type = EvaluationPolicyType.getType(typeStr);
    }

    /**
     * It sets the evaluation policy type.
     * 
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
