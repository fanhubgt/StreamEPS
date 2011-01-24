package org.streameps.operator.assertion;

public class OperatorAssertionFactory {

    private OperatorAssertionFactory factory = null;

    public OperatorAssertionFactory getInstance() {
	if (factory == null)
	    factory = new OperatorAssertionFactory();
	return factory;
    }

    public static ThresholdAssertion getAssertion(AssertionType type) {
	switch (type) {
	case EQUAL:
	    return new EqualAssertion();
	case GREATER:
	    return new GreaterThanAssertion();
	case GREATER_THAN_OR_EQUAL:
	    return new GreaterThanOrEqualAssertion();
	case LESS_THAN:
	    return new LessThanAssertion();
	case LESS_THAN_OR_EQUAL:
	    return new LessThanOrEqualAssertion();
	case NOT_EQUAL:
	    return new NotEqualAssertion();
	}
	throw new IllegalArgumentException();
    }

    public static ThresholdAssertion getAssertion(String type) {
	switch (AssertionType.getValue(type)) {
	case EQUAL:
	    return new EqualAssertion();
	case GREATER:
	    return new GreaterThanAssertion();
	case GREATER_THAN_OR_EQUAL:
	    return new GreaterThanOrEqualAssertion();
	case LESS_THAN:
	    return new LessThanAssertion();
	case LESS_THAN_OR_EQUAL:
	    return new LessThanOrEqualAssertion();
	case NOT_EQUAL:
	    return new NotEqualAssertion();
	}
	throw new IllegalArgumentException();
    }
}
