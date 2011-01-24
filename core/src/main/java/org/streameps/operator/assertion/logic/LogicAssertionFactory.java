package org.streameps.operator.assertion.logic;

public class LogicAssertionFactory {

    private LogicAssertionFactory factory = null;

    public LogicAssertionFactory getInstance() {
	if (factory == null)
	    factory = new LogicAssertionFactory();
	return factory;
    }

    public static LogicAssertion getAssertion(LogicType type) {
	switch (type) {
	case AND:
	    return new AndAssertion();
	case NOT:
	    return new NotAssertion();
	case OR:
	    return new OrAssertion();
	}
	throw new IllegalArgumentException();
    }
}
