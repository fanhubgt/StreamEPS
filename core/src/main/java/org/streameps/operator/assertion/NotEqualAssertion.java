package org.streameps.operator.assertion;

import org.streameps.aggregation.CounterValue;



/**
 * Not Equal Assertion
 */
public class NotEqualAssertion implements ThresholdAssertion {
	@Override
	public boolean assertEvent(CounterValue counter) {
	    if (counter.count != counter.value)
		return true;
	    return false;
	}

	@Override
	public String getAssertionType() {
	    return "neq";
	}
}
