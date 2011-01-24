
package org.streameps.operator.assertion;

import org.streameps.aggregation.CounterValue;



public interface ThresholdAssertion {

    public boolean assertEvent(CounterValue counter);
    
    public String getAssertionType();
}
