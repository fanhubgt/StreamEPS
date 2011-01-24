package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import org.streameps.aggregation.CounterValue;
import org.streameps.aggregation.TreeMapCounter;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;

public class ThresholdCountPE extends BasePattern {

    private String id = "s4:threshold:";
    private String assertionType;
    private TreeMapCounter mapCounter = null;
    public static final String THRESHOLD_VALUE = "thresholdValue";
    private Dispatcher dispatcher = null;
    private CounterValue counter;
    private String outputStreamName = null;
    private boolean match = false;

    public ThresholdCountPE() {
	mapCounter = new TreeMapCounter();
	counter = new CounterValue(0, 0);
    }

    @Override
    public String getId() {
	if (assertionType != null)
	    id += assertionType;
	return id;
    }

    public void setAssertion(String assertion) {
	this.assertionType = assertion;
    }

    public void setDispatcher(Dispatcher dispatcher) {
	this.dispatcher = dispatcher;
    }

    public void processEvent(Object event) {
	synchronized (this) {
	    PatternParameter threshParam = parameters.get(0);
	    String prop = threshParam.getPropertyName();
	    if (prop.equalsIgnoreCase(THRESHOLD_VALUE)) {
		long count = mapCounter.incrementAt(event);
		int threshold = (Integer) threshParam.getValue();
		assertionType = (String) threshParam.getRelation();
		counter.count = count;
		counter.value = threshold;
		ThresholdAssertion assertion = OperatorAssertionFactory
		        .getAssertion(assertionType);
		match = assertion.assertEvent(counter);
		if (match) {
		    for (Object key : mapCounter.getMap().keySet()) {
			this.matchingSet.add(key);
		    }
		    counter = new CounterValue(0, 0);
		    mapCounter.clear();
		    match = false;
		}
	    }
	}
    }
    
    /**
     * @param outputStreamName the outputStreamName to set
     */
    public void setOutputStreamName(String outputStreamName) {
	this.outputStreamName = outputStreamName;
    }

    @Override
    public void output() {
	if (matchingSet.size() > 0) {
	    dispatcher.dispatchEvent(outputStreamName, this.matchingSet);
	}
	this.matchingSet.clear();
    }

}
