package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import org.streameps.aggregation.CounterValue;
import org.streameps.aggregation.TreeMapCounter;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;


public class ThresholdMinPE extends BasePattern {

    private String THRESHOLD_NAME = "s4:theshold:min";
    private String assertionType;
    private TreeMapCounter mapCounter = null;
    public static final String THRESHOLD_MIN_ATTR = "minimum";
    private Dispatcher dispatcher = null;
    private CounterValue counter;
    private String outputStreamName = null;
    private boolean match = false;

    public ThresholdMinPE() {
	this.type = "Min Threshold";
    }

    @Override
    public void output() {
	if (this.matchingSet.size() > 0) {
	    dispatcher.dispatchEvent(outputStreamName, this.matchingSet);
	    this.matchingSet.clear();
	}
    }

    public void processEvent(Object event) {
	synchronized (this) {
	    PatternParameter threshParam = parameters.get(0);
	    String prop = threshParam.getPropertyName();
	    this.relevantEvents.add(event);
	    long count = mapCounter.incrementAt(event);
	    int threshold = (Integer) threshParam.getValue();
	    assertionType = (String) threshParam.getRelation();
	    counter.count = count;
	    counter.value = threshold;
	    ThresholdAssertion assertion = OperatorAssertionFactory
		    .getAssertion(assertionType);
	    match = assertion.assertEvent(counter);
	    if (match) {
		for (Object k : mapCounter.getMap().keySet())
		    this.matchingSet.add(k);
		mapCounter.clear();
		match = false;
	    }
	}
    }

    @Override
    public String getId() {
	return THRESHOLD_NAME;
    }

    /**
     * @param outputStreamName
     *            the outputStreamName to set
     */
    public void setOutputStreamName(String outputStreamName) {
	this.outputStreamName = outputStreamName;
    }

    public void setId(String name) {
	this.THRESHOLD_NAME = name;
    }
}
