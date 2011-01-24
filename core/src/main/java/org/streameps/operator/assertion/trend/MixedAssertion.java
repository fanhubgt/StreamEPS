package org.streameps.operator.assertion.trend;

import io.s4.schema.Schema.Property;

public class MixedAssertion implements TrendAssertion{

    /* (non-Javadoc)
     * @see io.s4.operator.assertion.trend.TrendAssertion#assessTrend(java.lang.String, io.s4.schema.Schema.Property, io.s4.schema.Schema.Property, java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean assessTrend(String attribute, Property prop1,
            Property prop2, Object e1, Object e2) {
	return false;
    }
    
    /* (non-Javadoc)
     * @see io.s4.operator.assertion.trend.TrendAssertion#getType()
     */
    @Override
    public String getType() {
	return TrendType.MIXED.toString();
    }

}
