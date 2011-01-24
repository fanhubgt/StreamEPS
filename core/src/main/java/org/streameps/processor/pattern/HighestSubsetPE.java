package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import org.streameps.aggregation.CounterValue;
import org.streameps.aggregation.SortedAccumulator;
import org.streameps.operator.assertion.EqualAssertion;

public class HighestSubsetPE extends BasePattern {

    private static String subset = "s4:highest";
    private SortedAccumulator accumulator;
    public static String HIGHEST_N_ATTR = "count";
    private int count = 0;
    private String key;
    private PatternParameter param = null;
    private boolean match = false;
    private Dispatcher dispatcher = null;
    private String streamName;

    public HighestSubsetPE() {
	accumulator = new SortedAccumulator();
    }

    @Override
    public void output() {
	if (matchingSet.size() > 0) {
	    dispatcher.dispatchEvent(streamName, this.matchingSet);
	    matchingSet.clear();
	    match = false;
	}
    }

    public void processEvent(Object event) {
	java.util.List<Object> added = accumulator.processAt(event.getClass()
	        .getName(), event);
	this.relevantEvents.add(event);
	if (param == null) {
	    param = this.parameters.get(0);
	    if (param.getPropertyName().equalsIgnoreCase(HIGHEST_N_ATTR)) {
		count = (Integer) param.getValue();
	    }
	}
	match = new EqualAssertion().assertEvent(new CounterValue(added.size(),
	        count));
	if (match) {
	    this.matchingSet.addAll(accumulator.highest(count));
	    accumulator.clear();
	}
    }

    @Override
    public String getId() {
	return subset;
    }

}
