package org.streameps.processor.pattern;

import io.s4.schema.Schema;
import io.s4.schema.Schema.Property;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.operator.assertion.trend.TrendAssertion;
import org.streameps.core.EventPropertyCache;
import org.streameps.dispatch.Dispatchable;
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
public class TrendPatternPE extends BasePattern {

    private TrendAssertion assertion;
    private String id = "s4:trend:";
    private Dispatchable dispatcher;
    private boolean match = false;
    private PatternParameter parameter = null;
    private SortedAccumulator unmatchAccumulator;
    private int count = 0, countAdded = 0, processCount = 0;
    private EventPropertyCache helper;

    public TrendPatternPE() {
        helper = new EventPropertyCache();
        logger = Logger.getLogger(TrendPatternPE.class);
        unmatchAccumulator = new SortedAccumulator();
    }

    @Override
    public void output() {
        int temp = count;
        if (count > 1) {
            for (int i = 0; i < temp; i++) {
                match = assertion.assessTrend(parameter.getPropertyName(),
                        helper.getPropertyFromCache(countAdded),
                        helper.getPropertyFromCache(i),
                        this.participantEvents.get(countAdded),
                        this.participantEvents.get(i));
                if(match)
                {
                    this.matchingSet.add(this.participantEvents.get(i));
                    countAdded = i;
                    logger.info("Trend pattern matching is found");
                } else
                {
                    Object event = this.participantEvents.get(i);
                    unmatchAccumulator.processAt(event.getClass().getName(), event);
                }
                 processCount += 1;
            }
        }
        if (matchingSet.size() > 0) {
            IMatchEventMap matchEventMap = new MatchEventMap(false);
            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.getClass().getName(), mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, getOutputStreamName());
           // matchingSet.clear();
        }
         if (unmatchAccumulator.totalCount() > 0) {
                IUnMatchEventMap unmatchEventMap = new UnMatchEventMap(false);
                TreeMap<Object, List<Object>> map = this.unmatchAccumulator.getMap();
                List<Object> unMatchList = map.firstEntry().getValue();
                for (Object mEvent : unMatchList) {
                    unmatchEventMap.put(mEvent.getClass().getName(), mEvent);
                }
                publishUnMatchEvents(unmatchEventMap, dispatcher, getOutputStreamName());
                unmatchAccumulator.clear();
            }
    }

    public void processEvent(Object event) {
        Schema sch = new Schema(event.getClass());
        this.participantEvents.add(event);
        if (parameter == null && count == 0) {
            parameter = this.parameters.get(0);
        }
        if (parameter != null) {
            Map<String, Property> schMap = sch.getProperties();
            Property prop = schMap.get(parameter.getPropertyName());
            helper.putPropertyToCache(count, prop);
            count++;
        }
        execPolicy("process");
    }

    private void reset() {
        count = 0;
        countAdded = 0;
        processCount = 0;
    }

    /**
     * @param assertion the assertion to set
     */
    public void setAssertion(TrendAssertion assertion) {
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
