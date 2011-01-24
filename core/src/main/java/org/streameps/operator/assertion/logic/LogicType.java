package org.streameps.operator.assertion.logic;

public enum LogicType {
    
    AND("and"), OR("or"), NOT("not");

    private String name;

    private LogicType(String name) {
	this.name = name;
    }

    public LogicType getType(String name) {
	for (LogicType type : LogicType.values()) {
	    if (type.name.equalsIgnoreCase(name))
		return type;
	}
	throw new IllegalArgumentException();
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
