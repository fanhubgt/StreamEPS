package org.streameps.aggregation;

public interface EventAccumulator {

    public enum AccumulationState {
	Clean, Dirty
    };

    public EventAccumulator clone();

    public void clear();
}
