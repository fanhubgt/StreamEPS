package org.streameps.processor.pattern.policy;

/**
 * Supported evaluation policy types for the pattern matchers.
 * 
 * @author Frank Appiah
 * @version 0.2.2
 */
public enum EvaluationPolicyType {

    IMMEDIATE("immediate"),
    DEFERRED("deferred");
    private String name;

    private EvaluationPolicyType(String name) {
	this.name = name;
    }

    public static EvaluationPolicyType getType(String type) {
	for (EvaluationPolicyType evaluationType : EvaluationPolicyType.values()) {
	    if (evaluationType.name.equalsIgnoreCase(type))
		return evaluationType;
	}
	throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        return name;
    }
}
