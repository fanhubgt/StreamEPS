package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import io.s4.schema.Schema;
import io.s4.schema.Schema.Property;
import java.util.Map;

import org.apache.log4j.Logger;
import org.streameps.operator.assertion.trend.TrendAssertion;
import org.streameps.core.EventPropertyCache;

public class TrendPatternPE extends BasePattern {

    private TrendAssertion assertion;
    private String id = "s4:trend:";
    private Dispatcher dispatch;
    private boolean match = false;
    private PatternParameter parameter = null;
    private int count = 0, countAdded=0;
    private EventPropertyCache helper;
    private String streamName;

    public TrendPatternPE() {
	helper = new EventPropertyCache();
        logger = Logger.getLogger(TrendPatternPE.class);
    }

    @Override
    public void output() {
	if (matchingSet.size() > 0) {
	    dispatch.dispatchEvent(streamName, this.matchingSet);
	    this.matchingSet.clear();
	}
    }

    public void processEvent(Object event) {
	Schema sch = new Schema(event.getClass());
	this.relevantEvents.add(event);
	if (parameter == null && count == 0) {
	    parameter = this.parameters.get(0);
	    this.matchingSet.add(this.relevantEvents.get(0));
            //return;
	}
	if (parameter != null) {
	    Map<String, Property> schMap = sch.getProperties();
	    Property prop = schMap.get(parameter.getPropertyName());
	    helper.putPropertyToCache(count, prop);
	    if (count > 0) {
		match = assertion
		        .assessTrend(parameter.getPropertyName(), helper
		                .getPropertyFromCache(count - 1), helper
		                .getPropertyFromCache(count), this.relevantEvents
		                .get(count - 1), this.relevantEvents.get(count));
		if (match) {
		    this.matchingSet.add(this.relevantEvents.get(count));
		    match = false;
		    logger.info("Trend pattern matching is found");
		}
	    }
	    count++;
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
