package org.streameps.processor.pattern;

import org.streameps.aggregation.collection.AssertionValuePair;
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
public class ThresholdMinPE<E> extends BasePattern<E> {

    private String THRESHOLD_NAME = "eps:theshold:min";
    private String assertionType;
    private Dispatchable dispatcher = null;
    private AssertionValuePair aggregateValue;
    private String outputStreamName, prop = null;
    private boolean match = false;
    private MinAggregation minAggregation;
    private IPatternParameter<E> threshParam = null;
    private double threshold;

    public ThresholdMinPE() {
        setPatternType("Min_Threshold");
        minAggregation = new MinAggregation();
        aggregateValue = new AssertionValuePair(0, 0);
    }

    @Override
    public void output() {
        for (E event : this.participantEvents) {
            minAggregation.process(aggregateValue, (Double) SchemaUtil.getPropertyValue(event, prop));
        }
        ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(assertionType);
        match = assertion.assertEvent(new AssertionValuePair(threshold, minAggregation.getValue()));
        if (match) {
            this.matchingSet.addAll(participantEvents);
        }
        if (this.matchingSet.size() > 0) {
            IMatchEventMap<E> matchEventMap = new MatchEventMap<E>(false);
            for (E mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.getClass().getName(), (E) postProcessBeforeSend(mEvent));
            }
            publishMatchEvents(matchEventMap, dispatcher, outputStreamName);
        } else {
            IUnMatchEventMap<E> unmatchEventMap = new UnMatchEventMap<E>(false);
            for (E mEvent : this.participantEvents) {
                unmatchEventMap.put(mEvent.getClass().getName(), (E) postProcessBeforeSend(mEvent));
            }
            publishUnMatchEvents(unmatchEventMap, dispatcher, outputStreamName);
        }
    }

    public void processEvent(E event) {
        synchronized (this) {
            if (threshParam == null) {
                threshParam = parameters.get(0);
                prop = threshParam.getPropertyName();
                threshold = (Double) threshParam.getValue();
                assertionType = (String) threshParam.getRelation();
            }
            this.participantEvents.add((E) preProcessOnRecieve(event));
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
