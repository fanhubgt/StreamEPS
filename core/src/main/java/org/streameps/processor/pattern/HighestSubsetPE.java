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
import org.streameps.aggregation.SortedAccumulator;
import org.streameps.operator.assertion.EqualAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;

public class HighestSubsetPE extends BasePattern {

    private static String subset = "s4:highest";
    private SortedAccumulator accumulator;
    public static String HIGHEST_N_ATTR = "count";
    private int count = 0;
    private String key;
    private PatternParameter param = null;
    private boolean match = false;
    private Dispatcher dispatcher = null;
    private String streamName;

    public HighestSubsetPE() {
        accumulator = new SortedAccumulator();
    }

    @Override
    public void output() {
        if (matchingSet.size() > 0) {
            System.out.println("outputing.");
            IMatchEventMap matchEventMap = new MatchEventMap(false);
            int pcount = 0;
            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.toString(), mEvent);
                pcount++;
                if (pcount == count) {
                    break;
                }
            }
            publishMatchEvents(matchEventMap, dispatcher, streamName);
            matchingSet.clear();
        }
    }

    public void processEvent(Object event) {
        final java.util.List<Object> added = accumulator.processAt(event.getClass().getName(), event);
        this.participantEvents.add(event);
        if (param == null) {
            param = this.parameters.get(0);
            if (param.getPropertyName().equalsIgnoreCase(HIGHEST_N_ATTR)) {
                count = (Integer) param.getValue();
            }
            if (eventName == null) {
                eventName = event.getClass().getName();
            }
        }
        synchronized (added) {
            match = new EqualAssertion().assertEvent(new AggregateValue(added.size(), count));
            if (match) {
                this.matchingSet.addAll(accumulator.highest(count));
                accumulator.clear();
                match = false;
            }
        }
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    @Override
    public String getId() {
        return subset;
    }
}
