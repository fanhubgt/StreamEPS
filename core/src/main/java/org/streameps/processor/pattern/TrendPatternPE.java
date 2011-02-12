package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import io.s4.schema.Schema;
import io.s4.schema.Schema.Property;
import java.util.Map;

import org.apache.log4j.Logger;
import org.streameps.operator.assertion.trend.TrendAssertion;
import org.streameps.core.EventPropertyCache;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;

public class TrendPatternPE extends BasePattern {

    private TrendAssertion assertion;
    private String id = "s4:trend:";
    private Dispatcher dispatch;
    private boolean match = false;
    private PatternParameter parameter = null;
    private int count = 0, countAdded = 0, processCount = 0;
    private EventPropertyCache helper;
    private String streamName;

    public TrendPatternPE() {
        helper = new EventPropertyCache();
        logger = Logger.getLogger(TrendPatternPE.class);
    }

    @Override
    public void output() {
        int temp = count;
        if (count > 0) {
            for (int i = (processCount + 1); i < temp; i++) {
                match = assertion.assessTrend(parameter.getPropertyName(),
                        helper.getPropertyFromCache(countAdded),
                        helper.getPropertyFromCache(i),
                        this.participantEvents.get(countAdded),
                        this.participantEvents.get(i));
                if (match) {
                    this.matchingSet.add(this.participantEvents.get(i));
                    countAdded = i;
                    match = false;
                    logger.info("Trend pattern matching is found");
                }
            }
        }
        if (matchingSet.size() > 0) {
            IMatchEventMap matchEventMap = new MatchEventMap(false);
            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.getClass().getName(), mEvent);
            }
            publishMatchEvents(matchEventMap, dispatch, streamName);
            //this.matchingSet.removeRange(processCount, temp);
        }
         processCount = temp;
    }

    public void processEvent(Object event) {
        Schema sch = new Schema(event.getClass());
        this.participantEvents.add(event);
        if (parameter == null && count == 0) {
            parameter = this.parameters.get(0);
            this.matchingSet.add(this.participantEvents.get(0));
        }
        if (parameter != null) {
            Map<String, Property> schMap = sch.getProperties();
            Property prop = schMap.get(parameter.getPropertyName());
            helper.putPropertyToCache(count, prop);
            count++;
            execPolicy("process");
        }
    }

    /**
     * @param assertion
     *            the assertion to set
     */
    public void setAssertion(TrendAssertion assertion) {
        this.assertion = assertion;
    }

    @Override
    public String getId() {
        return id + assertion.getType();
    }

    /**
     * @param dispatch
     *            the dispatch to set
     */
    public void setDispatch(Dispatcher dispatch) {
        this.dispatch = dispatch;
    }

    /**
     * @param streamName
     *            the streamName to set
     */
    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }
}
