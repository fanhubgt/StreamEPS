package org.streameps.operator.assertion.logic;

import io.s4.schema.Schema;
import io.s4.schema.Schema.Property;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.streameps.aggregation.CounterValue;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.PatternParameter;

public class AndAssertion implements LogicAssertion {

    @Override
    public boolean assertLogic(List<PatternParameter> params, Schema schema,
	    Object event) {
	Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
	Map<String, Property> schMap = schema.getProperties();
	for (PatternParameter param : params) {
	    Object value = param.getValue();
	    Property property = schMap.get(param.getPropertyName());
	    Method m = property.getGetterMethod();
	    try {
		if (m != null) {
		    Object result = m.invoke(event);
		    if (result instanceof String && value instanceof String) {
			resultMap.put(param.getPropertyName(), ((String) value)
			        .equalsIgnoreCase((String) result));
		    } else if (result instanceof Number
			    && value instanceof Number) {
			Number num_1 = (Number) value;
			Number num_2 = (Number) result;
			ThresholdAssertion assertion = OperatorAssertionFactory
			        .getAssertion(param.getRelation());
			if (num_1 instanceof Double || num_2 instanceof Double)
			    resultMap.put(param.getPropertyName(),
				    assertion.assertEvent(new CounterValue(
				            num_1.doubleValue(), num_2
				                    .doubleValue())));
			else if (num_1 instanceof Float
			        || num_2 instanceof Float)
			    resultMap
				    .put(param.getPropertyName(), assertion
				            .assertEvent(new CounterValue(num_1
				                    .floatValue(), num_2
				                    .floatValue())));
			else if (num_1 instanceof Integer
			        || num_2 instanceof Integer)
			    resultMap.put(param.getPropertyName(), assertion
				    .assertEvent(new CounterValue(num_1
				            .intValue(), num_2.intValue())));
			else if (num_1 instanceof Long || num_2 instanceof Long)
			    resultMap.put(param.getPropertyName(), assertion
				    .assertEvent(new CounterValue(num_1
				            .longValue(), num_2.longValue())));
		    }
		}
	    } catch (IllegalArgumentException e) {
		e.printStackTrace();
	    } catch (IllegalAccessException e) {
		e.printStackTrace();
	    } catch (InvocationTargetException e) {
		e.printStackTrace();
	    }
	}
	boolean sum = true;
	for (String key : resultMap.keySet()) {
	    boolean temp = resultMap.get(key);
	    sum &= temp;
	}
	return (sum == true);
    }

    /* (non-Javadoc)
     * @see io.s4.operator.assertion.logic.LogicAssertion#getType()
     */
    @Override
    public LogicType getType() {
	// TODO Auto-generated method stub
	return LogicType.AND;
    }
}
