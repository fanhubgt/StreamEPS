package org.streameps.operator.assertion;

public enum AssertionType {
    LESS_THAN("lt"), 
    GREATER_THAN_OR_EQUAL("geq"), 
    EQUAL("eq"), 
    GREATER("gt"), 
    LESS_THAN_OR_EQUAL("leq"), 
    NOT_EQUAL("neq");
    private String type;

    private AssertionType(String name) {
	this.type = name;
    }

    public static AssertionType getValue(String type) {
	for (AssertionType t : AssertionType.values()) {
	    if (t.type.equalsIgnoreCase(type)) {
		return t;
	    }
	}
	return null;
    }
}
