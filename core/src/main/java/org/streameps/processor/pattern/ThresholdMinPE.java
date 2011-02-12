package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import org.streameps.aggregation.AggregateValue;
import org.streameps.aggregation.MinAggregation;
import org.streameps.aggregation.collection.TreeMapCounter;
import org.streameps.core.util.SchemaUtil;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;


public class ThresholdMinPE extends BasePattern {

    private String THRESHOLD_NAME = "s4:theshold:min";
    private String assertionType;
    private TreeMapCounter mapCounter = null;
    public static final String THRESHOLD_MIN_ATTR = "minimum";
    private Dispatcher dispatcher = null;
    private AggregateValue aggregateValue;
    private String outputStreamName = null;
    private boolean match = false;
    private MinAggregation minAggregation;

    public ThresholdMinPE() {
	this.name = "Min Threshold";
        minAggregation=new MinAggregation();
        aggregateValue=new AggregateValue(0, 0);
    }

    @Override
    public void output() {
	if (this.matchingSet.size() > 0) {
	    IMatchEventMap matchEventMap = new MatchEventMap(false);
            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.getClass().getName(), mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, outputStreamName);
            matchingSet.clear();
	}
    }

    public void processEvent(Object event) {
	synchronized (this) {
	    PatternParameter threshParam = parameters.get(0);
	    String prop = threshParam.getPropertyName();
	    this.participantEvents.add(event);
	    long count = mapCounter.incrementAt(event);
	    int threshold = (Integer) threshParam.getValue();
	    assertionType = (String) threshParam.getRelation();
	    minAggregation.process(aggregateValue, (Double) SchemaUtil.getPropertyValue(event, prop));
	    ThresholdAssertion assertion = OperatorAssertionFactory
		    .getAssertion(assertionType);
	    match = assertion.assertEvent(new AggregateValue(threshold, minAggregation.getValue()));
	    if (match) {
		for (Object k : mapCounter.getMap().keySet())
		    this.matchingSet.add(k);
		mapCounter.clear();
		match = false;
                execPolicy("process");
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
