package org.streameps.processor.pattern;

import java.util.List;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.operator.assertion.trend.TrendAssertion;
import org.streameps.core.EventPropertyCache;
import org.streameps.core.schema.ISchemaProperty;
import org.streameps.core.util.SchemaUtil;
import org.streameps.dispatch.Dispatchable;
import org.streameps.operator.assertion.trend.ITrendObject;
import org.streameps.operator.assertion.trend.TrendObject;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;
import org.streameps.processor.pattern.listener.UnMatchEventMap;

/**
 * Trend patterns are patterns that trace the value of a specific attribute over
 * time. These patterns relate only to a single event type, so the relevant event
 * types list must have only one entry.
 * 
 * @author Frank Appiah
 */
public class TrendPatternPE<E> extends BasePattern<E> {

    private TrendAssertion<E> assertion;
    private String id = "s4:trend:";
    private Dispatchable dispatcher;
    private boolean match = false;
    private IPatternParameter<E> parameter = null;
    private SortedAccumulator<E> unmatchAccumulator;
    private int count = 0, countAdded = 0, processCount = 0;
    private EventPropertyCache helper;

    public TrendPatternPE() {
        helper = new EventPropertyCache();
        logger = Logger.getLogger(TrendPatternPE.class);
        unmatchAccumulator = new SortedAccumulator<E>();
        setPatternType(PatternType.TREND.getName());
    }

    public TrendPatternPE(TrendAssertion<E> assertion, Dispatchable dispatcher) {
        this.assertion = assertion;
        this.dispatcher = dispatcher;
        helper = new EventPropertyCache();
        logger = Logger.getLogger(TrendPatternPE.class);
        unmatchAccumulator = new SortedAccumulator<E>();
    }

    public TrendPatternPE(TrendAssertion<E> assertion) {
        this.assertion = assertion;
        helper = new EventPropertyCache();
        logger = Logger.getLogger(TrendPatternPE.class);
        unmatchAccumulator = new SortedAccumulator<E>();
    }

    @Override
    public void output() {
        int temp = count;
        if (count > 1) {
            for (int i = 1; i < temp; i++) {
                ITrendObject<E> trendObject = new TrendObject<E>();
                trendObject.setAttribute(parameter.getPropertyName());
                trendObject.getTrendList().add(helper.getPropertyFromCache(countAdded));
                trendObject.getTrendList().add(helper.getPropertyFromCache(i));
                match = assertion.assessTrend(trendObject);
                if (match) {
                    this.matchingSet.add(this.participantEvents.get(i));
                    countAdded = i;
                    logger.info("Trend pattern matching is found");
                } else {
                    //Object event = this.participantEvents.get(i);
                    for (E event : this.participantEvents) {
                        unmatchAccumulator.processAt(event.getClass().getName(), event);
                    }
                    matchingSet.clear();
                    break;
                }
                processCount += 1;
            }
        }
        if (getMatchingSet().size() > 0) {
            IMatchEventMap<E> matchEventMap = new MatchEventMap<E>(false);
            for (E mEvent : getMatchingSet()) {
                matchEventMap.put(mEvent.getClass().getName(), (E) postProcessBeforeSend(mEvent));
            }
            publishMatchEvents(matchEventMap, dispatcher, getOutputStreamName());
        }
        if (unmatchAccumulator.getSizeCount() > 0) {
            IUnMatchEventMap<E> unmatchEventMap = new UnMatchEventMap<E>(false);
            TreeMap<Object, List<E>> map = this.unmatchAccumulator.getMap();
            List<E> unMatchList = map.firstEntry().getValue();
            for (E mEvent : unMatchList) {
                unmatchEventMap.put(mEvent.getClass().getName(), (E) postProcessBeforeSend(mEvent));
            }
            publishUnMatchEvents(unmatchEventMap, dispatcher, getOutputStreamName());
            unmatchAccumulator.clear();
        }
    }

    public void processEvent(E event) {
        this.participantEvents.add((E) preProcessOnRecieve(event));
        if (parameter == null && count == 0) {
            parameter = this.parameters.get(0);
            this.matchingSet.add(event);
        }
        if (parameter != null) {
            ISchemaProperty<E> prop = (ISchemaProperty<E>) SchemaUtil.getProperty(event, parameter.getPropertyName());
            prop.setEvent(event);
            helper.putPropertyToCache(count, prop);
            count++;
        }
        execPolicy("process");
    }

    @Override
    public void reset() {
        count = 0;
        countAdded = 0;
        processCount = 0;
        this.participantEvents.clear();
        this.matchingSet.clear();
    }

    /**
     * @param assertion the assertion to set
     */
    public void setAssertion(TrendAssertion<E> assertion) {
        this.assertion = assertion;
    }

    public String getId() {
        return id + assertion.getType();
    }

    /**
     * @param dispatch  the dispatch to set
     */
    public void setDispatch(Dispatchable dispatch) {
        this.dispatcher = dispatch;
    }
}
