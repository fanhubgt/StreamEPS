package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import org.streameps.aggregation.CounterValue;
import org.streameps.aggregation.SortedAccumulator;
import org.streameps.operator.assertion.EqualAssertion;


public class LowestSubsetPE extends BasePattern {

    public static String SUBSET_NAME = "s4:lowest";
    public static String LOWEST_N_ATTR = "count";
    private SortedAccumulator accumulator;
    private int count = 0;
    private PatternParameter param = null;
    private boolean match = false;
    private Dispatcher dispatcher = null;
    private String streamName;

    public LowestSubsetPE() {
	accumulator = new SortedAccumulator();
    }

    @Override
    public void output() {
	if (this.matchingSet.size() > 0) {
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
	    if (param.getPropertyName().equalsIgnoreCase(LOWEST_N_ATTR)) {
		count = (Integer) param.getValue();
	    }
	}
	match = new EqualAssertion().assertEvent(new CounterValue(added.size(),
	        count));
	if (match) {
	    this.matchingSet.addAll(accumulator.lowest(count));
	    accumulator.clear();
	}
    }

    @Override
    public String getId() {
	return SUBSET_NAME;
    }

    /**
     * @param dispatcher
     *            the dispatcher to set
     */
    public void setDispatcher(Dispatcher dispatcher) {
	this.dispatcher = dispatcher;
    }

    /**
     * @param streamName
     *            the streamName to set
     */
    public void setStreamName(String streamName) {
	this.streamName = streamName;
    }
}
