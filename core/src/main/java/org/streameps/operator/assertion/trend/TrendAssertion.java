package org.streameps.operator.assertion.trend;

import io.s4.schema.Schema.Property;

public interface TrendAssertion {

    public boolean assessTrend(String attribute, Property prop1,
	    Property prop2, Object e1, Object e2);

    public String getType();

}
