package org.streameps.aggregation;


public interface Aggregation {

    public void process(CounterValue cv, double value );
}
