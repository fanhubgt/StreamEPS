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
import org.streameps.aggregation.TreeMapCounter;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;

public class ThresholdCountPE extends BasePattern {

    private String id = "s4:threshold:";
    private String assertionType;
    private TreeMapCounter mapCounter = null;
    public static final String THRESHOLD_VALUE = "thresholdValue";
    private Dispatcher dispatcher = null;
    private AggregateValue counter;
    private String outputStreamName = null;
    private boolean match = false;

    public ThresholdCountPE() {
        mapCounter = new TreeMapCounter();
        counter = new AggregateValue(0, 0);
    }

    @Override
    public String getId() {
        if (assertionType != null) {
            id += assertionType;
        }
        return id;
    }

    public void setAssertion(String assertion) {
        this.assertionType = assertion;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void processEvent(Object event) {
        synchronized (this) {
            PatternParameter threshParam = parameters.get(0);
            String prop = threshParam.getPropertyName();
            if (prop.equalsIgnoreCase(THRESHOLD_VALUE)) {
                long count = mapCounter.incrementAt(event);
                int threshold = (Integer) threshParam.getValue();
                assertionType = (String) threshParam.getRelation();
                counter.threshold = threshold;
                counter.value = count;
                ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(assertionType);
                match = assertion.assertEvent(counter);
                if (match) {
                    for (Object key : mapCounter.getMap().keySet()) {
                        this.matchingSet.add(key);
                    }
                    counter = new AggregateValue(0, 0);
                    mapCounter.clear();
                    match = false;
                }
            }
        }
    }

    /**
     * @param outputStreamName the outputStreamName to set
     */
    public void setOutputStreamName(String outputStreamName) {
        this.outputStreamName = outputStreamName;
    }

    @Override
    public void output() {
        if (matchingSet.size() > 0) {
            IMatchEventMap matchEventMap = new MatchEventMap(false);
            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(eventName, mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, outputStreamName);
            matchingSet.clear();
        }
    }
}
