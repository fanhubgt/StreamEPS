package org.streameps.operator.assertion.logic;

import io.s4.schema.Schema;
import io.s4.schema.Schema.Property;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.streameps.aggregation.CounterValue;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.PatternParameter;

public class OrAssertion implements LogicAssertion {

    private Logger logger = Logger.getLogger(OrAssertion.class);

    @Override
    public boolean assertLogic(List<PatternParameter> params, Schema schema,
	    Object event) {
	Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
	Map<String, Property> schMap = schema.getProperties();
	for (PatternParameter p : params) {
	    Object value = p.getValue();
	    Property property = schMap.get(p.getPropertyName());
	    Method m = property.getGetterMethod();
	    try {
		if (m != null) {
		    Object result = m.invoke(event);
		    if (result instanceof String && value instanceof String) {
			resultMap.put(p.getPropertyName(), ((String) value)
			        .equalsIgnoreCase((String) result));
		    } else if (result instanceof Number
			    && value instanceof Number) {
			Number num_1 = (Number) value;
			Number num_2 = (Number) result;
			ThresholdAssertion assertion = OperatorAssertionFactory
			        .getAssertion(p.getRelation());

			if (num_1 instanceof Double || num_2 instanceof Double)
			    resultMap.put(p.getPropertyName(),
				    assertion.assertEvent(new CounterValue(
				            num_1.doubleValue(), num_2
				                    .doubleValue())));
			else if (num_1 instanceof Float
			        || num_2 instanceof Float)
			    resultMap
				    .put(p.getPropertyName(), assertion
				            .assertEvent(new CounterValue(num_1
				                    .floatValue(), num_2
				                    .floatValue())));
			else if (num_1 instanceof Integer
			        || num_2 instanceof Integer)
			    resultMap.put(p.getPropertyName(), assertion
				    .assertEvent(new CounterValue(num_1
				            .intValue(), num_2.intValue())));
			else if (num_1 instanceof Long || num_2 instanceof Long)
			    resultMap.put(p.getPropertyName(), assertion
				    .assertEvent(new CounterValue(num_1
				            .longValue(), num_2.longValue())));
		    }
		}
	    } catch (IllegalArgumentException e) {
		logger.warn(e);
	    } catch (IllegalAccessException e) {
		logger.warn(e);
	    } catch (InvocationTargetException e) {
		logger.warn(e);
	    }
	}
	boolean sum = false;
	for (String key : resultMap.keySet()) {
	    boolean temp = resultMap.get(key);
	    sum |= temp;
	}
	return (sum == true);
    }

    /* (non-Javadoc)
     * @see io.s4.operator.assertion.logic.LogicAssertion#getType()
     */
    @Override
    public LogicType getType() {
	return LogicType.OR;
    }
}
