package org.streameps.operator.assertion;

import org.streameps.aggregation.CounterValue;



/***
 * Less Than or Equal Assertion
 */
public class LessThanOrEqualAssertion implements ThresholdAssertion {
	@Override
	public boolean assertEvent(CounterValue counter) {
	    if (counter.value <= counter.count)
		return true;
	    return false;
	}

	@Override
	public String getAssertionType() {
	    return "leq";
	}
}