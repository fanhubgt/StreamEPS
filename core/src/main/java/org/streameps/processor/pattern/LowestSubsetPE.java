/*
 * ====================================================================
 *  StreamEPS Platform
 *
 *  Distributed under the Modified BSD License.
 *  Copyright notice: The copyright for this software and a full listing
 *  of individual contributors are as shown in the packaged copyright.txt
 *  file.
 *  All rights reserved.
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  - Redistributions in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 *  - Neither the name of the ORGANIZATION nor the names of its contributors may
 *  be used to endorse or promote products derived from this software without
 *  specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 *  AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 *  IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *  DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 *  FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 *  DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 *  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 *  OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 *  USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *  =============================================================================
 */
package org.streameps.processor.pattern;

import io.s4.dispatcher.Dispatcher;
import org.streameps.aggregation.AggregateValue;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.operator.assertion.EqualAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;

public class LowestSubsetPE extends BasePattern {

    public static String SUBSET_NAME = "s4:lowest";
    public static String LOWEST_N_ATTR = "count";
    private SortedAccumulator accumulator;
    private int count = 0;
    private PatternParameter param = null;
    private boolean match = false;
    private Dispatcher dispatcher = null;
    private String streamName;

    public LowestSubsetPE() {
        accumulator = new SortedAccumulator();
    }

    @Override
    public void output() {
        if (this.matchingSet.size() > 0) {
            IMatchEventMap matchEventMap = new MatchEventMap(false);
            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(eventName, mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, streamName);
            matchingSet.clear();
        }
    }

    public void processEvent(Object event) {
        java.util.List<Object> added = accumulator.processAt(event.getClass().getName(), event);
        this.participantEvents.add(event);
        if (param == null) {
            param = this.parameters.get(0);
            if (param.getPropertyName().equalsIgnoreCase(LOWEST_N_ATTR)) {
                count = (Integer) param.getValue();
            }
        }
        match = new EqualAssertion().assertEvent(new AggregateValue(added.size(), count));
        synchronized (added) {
            if (match) {
                this.matchingSet.addAll(accumulator.lowest(count));
                accumulator.clear();
                match = false;
            }
        }
        //set event name if null
        if (eventName == null) {
            eventName = event.getClass().getName();
        }
    }

    @Override
    public String getId() {
        return SUBSET_NAME;
    }

    /**
     * @param dispatcher
     *            the dispatcher to set
     */
    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * @param streamName
     *            the streamName to set
     */
    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }
}
