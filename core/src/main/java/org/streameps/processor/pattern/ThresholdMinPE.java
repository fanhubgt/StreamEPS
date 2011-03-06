package org.streameps.processor.pattern;

import org.streameps.aggregation.AggregateValue;
import org.streameps.aggregation.MinAggregation;
import org.streameps.core.util.SchemaUtil;
import org.streameps.dispatch.Dispatchable;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;
import org.streameps.processor.pattern.listener.UnMatchEventMap;

/**
 * The value min pattern is satisfied when the minimal value of a specific attribute
 * over all the participant events satisfies the value min threshold assertion.
 * 
 * @author Frank Appiah
 */
public class ThresholdMinPE extends BasePattern {

    private String THRESHOLD_NAME = "s4:theshold:min";
    private String assertionType;
    private Dispatchable dispatcher = null;
    private AggregateValue aggregateValue;
    private String outputStreamName, prop = null;
    private boolean match = false;
    private MinAggregation minAggregation;
    private PatternParameter threshParam = null;
    private double threshold;

    public ThresholdMinPE() {
        setPatternType("Min_Threshold");
        minAggregation = new MinAggregation();
        aggregateValue = new AggregateValue(0, 0);
    }

    @Override
    public void output() {
        for (Object event : this.participantEvents) {
            minAggregation.process(aggregateValue, (Double) SchemaUtil.getPropertyValue(event, prop));
        }
        ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(assertionType);
        match = assertion.assertEvent(new AggregateValue(threshold, minAggregation.getValue()));
        if (match) {
            this.matchingSet.addAll(participantEvents);
        }
        if (this.matchingSet.size() > 0) {
            IMatchEventMap matchEventMap = new MatchEventMap(false);
            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.getClass().getName(), mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, outputStreamName);
            matchingSet.clear();
        } else {
            IUnMatchEventMap unmatchEventMap = new UnMatchEventMap(false);
            for (Object mEvent : this.participantEvents) {
                unmatchEventMap.put(mEvent.getClass().getName(), mEvent);
            }
            publishUnMatchEvents(unmatchEventMap, dispatcher, outputStreamName);
            this.participantEvents.clear();
        }
    }

    public void processEvent(Object event) {
        synchronized (this) {
            if (threshParam == null) {
                threshParam = parameters.get(0);
                prop = threshParam.getPropertyName();
                threshold = (Double) threshParam.getValue();
                assertionType = (String) threshParam.getRelation();
            }
            this.participantEvents.add(event);
            execPolicy("process");
        }
    }

    public String getId() {
        return THRESHOLD_NAME;
    }

    public void setId(String name) {
        this.THRESHOLD_NAME = name;
    }

    public void setDispatcher(Dispatchable dispatcher) {
        this.dispatcher = dispatcher;
    }
    
}
