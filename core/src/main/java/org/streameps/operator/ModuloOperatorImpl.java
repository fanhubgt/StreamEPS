package org.streameps.operator;

/**
 * 
 * It helps evaluate the A % B expressions.
 */
public class ModuloOperatorImpl implements S4Operator {

    @Override
    public Object evaluate(Object... o) {
	if (!(o[0] instanceof Number) || !(o[1] instanceof Number))
	    return null;
	Number num_1 = (Number) o[0];
	Number num_2 = (Number) o[1];
	if (num_1 instanceof Double || num_2 instanceof Double)
	    return num_1.doubleValue() % num_2.doubleValue();
	else if (num_1 instanceof Float || num_2 instanceof Float)
	    return num_1.floatValue() % num_2.floatValue();
	else if (num_1 instanceof Integer || num_2 instanceof Integer)
	    return num_1.intValue() % num_2.intValue();
	else if (num_1 instanceof Long || num_2 instanceof Long)
	    return num_1.longValue() % num_2.longValue();
	return num_1.byteValue() * num_2.byteValue();
    }

    @Override
    public String getOperator() {
	return "%";
    }

    @Override
    public String toString() {
	return getOperator();
    }

}
