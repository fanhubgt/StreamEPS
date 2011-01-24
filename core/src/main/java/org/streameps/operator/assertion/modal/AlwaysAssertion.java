package org.streameps.operator.assertion.modal;

import io.s4.schema.Schema;
import io.s4.schema.Schema.Property;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import org.streameps.aggregation.CounterValue;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.PatternParameter;

/**
 * Always is a modal assertion which is matched if all event instances in the
 * participant event set satisfy an assertion.
 */
public class AlwaysAssertion implements ModalAssertion {

    @Override
    public boolean assertModel(List<PatternParameter> params, Schema schema,
	    Object event) {
	Map<String, Property> schMap = schema.getProperties();
	for (PatternParameter p : params) {
	    Object value = p.getValue();
	    Property property = schMap.get(p.getPropertyName());
	    Method m = property.getGetterMethod();
	    try {
		if (m != null) {
		    Object result = m.invoke(event);
		    if (result instanceof String && value instanceof String) {
			return ((String) value)
			        .equalsIgnoreCase((String) result);
		    } else if (result instanceof Number
			    && value instanceof Number) {
			Number num_1 = (Number) value;
			Number num_2 = (Number) result;
			ThresholdAssertion assertion = OperatorAssertionFactory
			        .getAssertion(p.getRelation());

			if (num_1 instanceof Double || num_2 instanceof Double)
			    return assertion.assertEvent(new CounterValue(num_1
				    .doubleValue(), num_2.doubleValue()));
			else if (num_1 instanceof Float
			        || num_2 instanceof Float)
			    return assertion.assertEvent(new CounterValue(num_1
				    .floatValue(), num_2.floatValue()));
			else if (num_1 instanceof Integer
			        || num_2 instanceof Integer)
			    return assertion.assertEvent(new CounterValue(num_1
				    .intValue(), num_2.intValue()));
			else if (num_1 instanceof Long || num_2 instanceof Long)
			    return assertion.assertEvent(new CounterValue(num_1
				    .longValue(), num_2.longValue()));
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
	return false;
    }

}
