package org.streameps.operator;

public class OperatorFactory {

    private OperatorFactory factory = null;

    public OperatorFactory getInstance() {
	if (factory == null) {
	    factory = new OperatorFactory();
	}
	return factory;
    }

    public static S4Operator getOperation(String type) {
	switch (OperatorType.getType(type)) {
	case ADD:
	    return new AddOperatorImpl();
	case DIVIDE:
	    return new DivideOperatorImpl();
	case MULTIPLY:
	    return new MultiplyOperatorImpl();
	case MODULO:
	    return new ModuloOperatorImpl();
	case SUBTRACT:
	    return new SubstractOperatorImpl();
	}
	throw new IllegalArgumentException();
    }
}
