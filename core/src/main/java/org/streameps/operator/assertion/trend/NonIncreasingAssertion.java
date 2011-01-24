package org.streameps.operator.assertion.trend;

import io.s4.schema.Schema.Property;
import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.streameps.aggregation.CounterValue;
import org.streameps.operator.assertion.GreaterThanOrEqualAssertion;


/**
 *  The non-increasing pattern is satisfied if the value of a given attribute 
 *  does not increase within the given context.
 *  It assess if e1 << e2 => e1.A >= e2.A where A is a number attribute to be compared.
 */
public class NonIncreasingAssertion implements TrendAssertion {

    private Logger logger=Logger.getLogger(NonIncreasingAssertion.class);
    /*
     * (non-Javadoc)
     * 
     * @see
     * io.s4.operator.assertion.trend.TrendAssertion#assessTrend(java.lang.String
     * , io.s4.schema.Schema.Property, io.s4.schema.Schema.Property,
     * java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean assessTrend(String attribute, Property prop1,
	    Property prop2, Object e1, Object e2) {
	try {
	    if (prop1.getName().equalsIgnoreCase(attribute)
		    && prop2.getName().equalsIgnoreCase(attribute)) {
		Object val1 = prop1.getGetterMethod().invoke(e1);
		Object val2 = prop2.getGetterMethod().invoke(e2);
		if (val1 != null && val2 != null) {
		    Number num_1 = (Number) val1;
		    Number num_2 = (Number) val2;
		    if (num_1 instanceof Double || num_2 instanceof Double)
			return new GreaterThanOrEqualAssertion()
			        .assertEvent(new CounterValue(num_1
			                .doubleValue(), num_2.doubleValue()));
		    else if (num_1 instanceof Float || num_2 instanceof Float)
			return new GreaterThanOrEqualAssertion()
			        .assertEvent(new CounterValue(num_1
			                .floatValue(), num_2.floatValue()));
		    else if (num_1 instanceof Integer
			    || num_2 instanceof Integer)
			return new GreaterThanOrEqualAssertion()
			        .assertEvent(new CounterValue(num_1.intValue(),
			                num_2.intValue()));
		    else if (num_1 instanceof Long || num_2 instanceof Long)
			return new GreaterThanOrEqualAssertion()
			        .assertEvent(new CounterValue(
			                num_1.longValue(), num_2.longValue()));
		}
	    }
	} catch (IllegalArgumentException e) {
	    logger.warn(e);
	} catch (IllegalAccessException e) {
	    logger.warn(e);
	} catch (InvocationTargetException e) {
	    logger.warn(e);
	}
	return false;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see io.s4.operator.assertion.trend.TrendAssertion#getType()
     */
    @Override
    public String getType() {
	// TODO Auto-generated method stub
	return TrendType.NON_DECREASING.toString();
    }
}
