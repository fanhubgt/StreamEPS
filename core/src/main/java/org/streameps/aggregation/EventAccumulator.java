package org.streameps.aggregation;

import java.io.Serializable;

public interface EventAccumulator extends Serializable{

    public enum AccumulationState {
	Clean, Dirty
    };

    public EventAccumulator clone();

    public void clear();
}
