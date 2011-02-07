package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import org.streameps.aggregation.AggregateValue;
import org.streameps.aggregation.MaxAggregation;
import org.streameps.aggregation.collection.TreeMapCounter;
import org.streameps.core.util.SchemaUtil;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;

public class ThresholdMaxPE extends BasePattern {

    private static String THRESHOLD_NAME = "s4:thesholdmax";
    private String assertionType;
    private TreeMapCounter mapCounter = null;
    public static final String THRESHOLD_MAX_ATTR = "maximum";
    private Dispatcher dispatcher = null;
    private AggregateValue aggregateValue;
    private String outputStreamName = null;
    private boolean match = false;
    private MaxAggregation maxAggregation;
    private int count=0;

    public ThresholdMaxPE() {
        maxAggregation = new MaxAggregation();
        aggregateValue = new AggregateValue(0, 0);
    }

    @Override
    public void output() {
        int temp=count;
        if (matchingSet.size() > 0) {
             IMatchEventMap matchEventMap = new MatchEventMap(false);
            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.toString(), mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, outputStreamName);

            matchingSet.clear();
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
               count= (int) mapCounter.incrementAt(event);
                int threshold = (Integer) threshParam.getValue();
                assertionType = (String) threshParam.getRelation();
                maxAggregation.process(aggregateValue, (Double) SchemaUtil.getPropertyValue(event, prop));
                ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(assertionType);
                match = assertion.assertEvent(new AggregateValue(threshold, maxAggregation.getValue()));
                if (match) {
                    for (Object k : mapCounter.getMap().keySet()) {
                        this.matchingSet.add(k);
                    }
                    mapCounter.clear();
                    match = false;
                }
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
