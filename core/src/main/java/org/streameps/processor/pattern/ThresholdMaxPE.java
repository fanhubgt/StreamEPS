package org.streameps.processor.pattern;

import org.streameps.aggregation.collection.AssertionValuePair;
import org.streameps.aggregation.MaxAggregation;
import org.streameps.core.util.SchemaUtil;
import org.streameps.dispatch.Dispatchable;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;
import org.streameps.processor.pattern.listener.UnMatchEventMap;

/**
 * The value max pattern is satisfied when the maximal value of a specific attribute
 * over all the participant events satisfies the value max threshold assertion.
 * 
 * @author Frank Appiah
 */
public class ThresholdMaxPE<E> extends BasePattern<E> {

    private static String THRESHOLD_NAME = "eps:thesholdmax";
    private String assertionType, prop;
    public static final String THRESHOLD_MAX_ATTR = "maximum";
    private Dispatchable dispatcher = null;
    private AssertionValuePair aggregateValue;
    private IPatternParameter<E> threshParam = null;
    private boolean match = false;
    private MaxAggregation maxAggregation;
    private double threshold = 0;

    public ThresholdMaxPE() {
        maxAggregation = new MaxAggregation();
        aggregateValue = new AssertionValuePair(0, 0);
        setPatternType(PatternType.THRESHOLD_MAX.getName());
    }

    @Override
    public void output() {
        for (E event : this.participantEvents) {
            maxAggregation.process(aggregateValue, (Double) SchemaUtil.getPropertyValue(event, prop));
        }
        ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(assertionType);
        match = assertion.assertEvent(new AssertionValuePair(threshold, maxAggregation.getValue()));
        if (match) {
            this.matchingSet.addAll(this.participantEvents);
        }
        if (matchingSet.size() > 0) {
            IMatchEventMap<E> matchEventMap = new MatchEventMap<E>(false);
            for (E mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.getClass().getName(), mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, getOutputStreamName());
        } else {
            IUnMatchEventMap<E> unmatchEventMap = new UnMatchEventMap<E>(false);
            for (E mEvent : this.participantEvents) {
                unmatchEventMap.put(mEvent.getClass().getName(), mEvent);
            }
            publishUnMatchEvents(unmatchEventMap, dispatcher, getOutputStreamName());
        }

    }

    public String getId() {
        return THRESHOLD_NAME;
    }

    public void processEvent(E event) {
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

    /**
     * @param dispatcher
     *            the dispatcher to set
     */
    public void setDispatcher(Dispatchable dispatcher) {
        this.dispatcher = dispatcher;
    }
}
