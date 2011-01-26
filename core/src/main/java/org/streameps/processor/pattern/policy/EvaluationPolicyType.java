package org.streameps.processor.pattern.policy;

public enum EvaluationPolicyType {

    IMMEDIATE("immediate"), DEFERRED("deferred");
    private String name;

    private EvaluationPolicyType(String name) {
	this.name = name;
    }

    public EvaluationPolicyType getType(String type) {
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
