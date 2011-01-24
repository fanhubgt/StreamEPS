package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import java.util.List;
import org.streameps.aggregation.CounterValue;
import org.streameps.aggregation.TreeMapCounter;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;



public class ThresholdAveragePE extends BasePattern {

    private static String THRESHOLD_NAME = "s4:thesholdavg";
    private String assertionType;
    private TreeMapCounter mapCounter = null;
    public static final String THRESHOLD_AVG_ATTR = "average";
    private Dispatcher dispatcher = null;
    private CounterValue counter;
    private String outputStreamName = null;
    private boolean match = false;
    
    @Override
    public void output() {
    }

    @Override
    public String getId() {
	return null;
    }
    
    public void processEvent(Object event) {
	synchronized (this) {
	    PatternParameter threshParam = parameters.get(0);
	    String prop = threshParam.getPropertyName();
	    if (prop.equalsIgnoreCase(THRESHOLD_AVG_ATTR)) {
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
    
    private double calculateAvg(List<Object> events)
    {
	return 0.0;
    }

}
