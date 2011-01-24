package org.streameps.operator.assertion;

import org.streameps.aggregation.CounterValue;



/**
 * Equal Assertion
 */
public class EqualAssertion implements ThresholdAssertion {
    
	@Override
	public boolean assertEvent(CounterValue counter) {
	    if (counter.count == counter.value)
		return true;
	    return false;
	}

	@Override
	public String getAssertionType() {
	    return "eq";
	}
}
