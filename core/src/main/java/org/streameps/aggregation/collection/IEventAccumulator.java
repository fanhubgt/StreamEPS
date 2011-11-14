package org.streameps.aggregation.collection;

import java.io.Serializable;

public interface IEventAccumulator extends Serializable, IAccumulator {

    public enum AccumulationState {

        Clean, Dirty
    };

    public IEventAccumulator clone();

    public void clear();
}
