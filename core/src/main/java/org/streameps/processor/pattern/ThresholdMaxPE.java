package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import org.streameps.aggregation.CounterValue;
import org.streameps.aggregation.TreeMapCounter;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;


public class ThresholdMaxPE extends BasePattern {

    private static String THRESHOLD_NAME = "s4:thesholdmax";
    private String assertionType;
    private TreeMapCounter mapCounter = null;
    public static final String THRESHOLD_MAX_ATTR = "maximum";
    private Dispatcher dispatcher = null;
    private CounterValue counter;
    private String outputStreamName = null;
    private boolean match = false;

    @Override
    public void output() {
	if (match) {
	    dispatcher.dispatchEvent(outputStreamName, this.relevantEvents);
	    match = false;
	}
    }

    @Override
    public String getId() {
	return THRESHOLD_NAME;
    }

    public void processEvent(Object event) {
	synchronized (this) {
	    PatternParameter threshParam = parameters.get(0);
	    String prop = threshParam.getPropertyName();
	    if (prop.equalsIgnoreCase(THRESHOLD_MAX_ATTR)) {
		long count = mapCounter.incrementAt(event);
		int threshold = (Integer) threshParam.getValue();
		assertionType = (String) threshParam.getRelation();
		counter.count = count;
		counter.value = threshold;
		ThresholdAssertion assertion = OperatorAssertionFactory
		        .getAssertion(assertionType);
		match = assertion.assertEvent(counter);
	    }
	}
    }

    /**
     * @param dispatcher
     *            the dispatcher to set
     */
    public void setDispatcher(Dispatcher dispatcher) {
	this.dispatcher = dispatcher;
    }

    /**
     * @param outputStreamName
     *            the outputStreamName to set
     */
    public void setOutputStreamName(String outputStreamName) {
	this.outputStreamName = outputStreamName;
    }

}
