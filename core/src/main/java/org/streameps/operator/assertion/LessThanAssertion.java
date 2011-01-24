package org.streameps.operator.assertion;

import org.streameps.aggregation.CounterValue;



/**
 * Less Than Assertion
 */
public class LessThanAssertion implements ThresholdAssertion {
	@Override
	public boolean assertEvent(CounterValue counter) {
	    if (counter.value < counter.count)
		return true;
	    return false;
	}

	@Override
	public String getAssertionType() {
	    return "lt";
	}
}