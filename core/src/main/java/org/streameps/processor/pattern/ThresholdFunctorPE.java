package org.streameps.processor.pattern;

import org.streameps.aggregation.AggregateValue;
import org.streameps.aggregation.collection.TreeMapCounter;
import org.streameps.dispatch.Dispatchable;
import org.streameps.operator.assertion.FunctorRegistry;

public class ThresholdFunctorPE extends BasePattern {

    private String outputStreamName;
    private FunctorRegistry functorRegistry;
    private String FUNCTOR_NAME = "functor";
    private String id = "s4:threshold:functor";
    private TreeMapCounter mapCounter = null;
    private boolean match = false;
    private Dispatchable dispatcher;
    private AggregateValue counter;
    
    /**
     * 
     */
    public ThresholdFunctorPE() {
	counter=new AggregateValue(0, 0);
    }

    @Override
    public void output() {
	
	matchingSet.clear();
    }

    public void processEvent(Object event) {
	synchronized (this) {
	    PatternParameter threshParam = parameters.get(0);
	    String prop = threshParam.getPropertyName();
	    if (prop.equalsIgnoreCase(FUNCTOR_NAME)) {
		long count = mapCounter.incrementAt(event);
		int threshold = (Integer) threshParam.getValue();
		//assertionType = (String) threshParam.getRelation();
		counter.threshold = threshold;
		counter.value = count;
		//match = assertion.assertEvent(counter);
		if (match) {
		    for (Object key : mapCounter.getMap().keySet()) {
			this.matchingSet.add(key);
		    }
		    counter = new AggregateValue(0, 0);
		    mapCounter.clear();
		    match = false;
                    execPolicy("process");
		}
	    }
	}
    }

    public String getId() {
	return id;
    }

    /**
     * @param functorRegistry the functorRegistry to set
     */
    public void setFunctorRegistry(FunctorRegistry functorRegistry) {
	this.functorRegistry = functorRegistry;
    }

    /**
     * @param outputStreamName
     *            the outputStreamName to set
     */
    public void setOutputStreamName(String outputStreamName) {
	this.outputStreamName = outputStreamName;
    }

    /**
     * @param dispatcher the dispatcher to set
     */
    public void setDispatcher(Dispatchable dispatcher) {
	this.dispatcher = dispatcher;
    }
    
}
