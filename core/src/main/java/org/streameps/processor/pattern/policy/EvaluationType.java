package org.streameps.processor.pattern.policy;

public enum EvaluationType {

    IMMEDIATE("immediate"), DEFERRED("deferred");
    private String name;

    private EvaluationType(String name) {
	this.name = name;
    }

    public EvaluationType getType(String type) {
	for (EvaluationType evaluationType : EvaluationType.values()) {
	    if (evaluationType.name.equalsIgnoreCase(type))
		return evaluationType;
	}
	throw new IllegalArgumentException();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return name;
    }
}
