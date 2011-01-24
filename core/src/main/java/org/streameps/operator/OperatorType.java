package org.streameps.operator;

public enum OperatorType {
    ADD("add"), 
    DIVIDE("divide"), 
    MODULO("modulo"), 
    MULTIPLY("multiply"), 
    SUBTRACT("subtract");

    private String name;

    private OperatorType(String name) {
	this.name = name;
    }

    public static OperatorType getType(String name) {
	for (OperatorType type : OperatorType.values()) {
	    if (type.name.equalsIgnoreCase(name))
		return type;
	}
	throw new IllegalArgumentException();
    }
    
}
