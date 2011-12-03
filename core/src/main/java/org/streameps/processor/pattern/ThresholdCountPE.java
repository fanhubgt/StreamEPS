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

import java.util.List;
import java.util.TreeMap;
import org.streameps.aggregation.collection.AssertionValuePair;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.dispatch.Dispatchable;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;
import org.streameps.processor.pattern.listener.UnMatchEventMap;

/**
 * The count pattern counts the number of participant event instances and tests
 * this value using a threshold assertion. This assertion uses one of the relations,
 * >, <, =, >=, <=, !=, to test the value against a constant threshold value.
 *
 * This pattern should be used with the <b>IMMEDIATE</b> evaluation policy.
 * 
 * @author Frank Appiah
 * @version 0.2.2
 */
public class ThresholdCountPE<E> extends BasePattern<E> {

    private String id = "eps:threshold:";
    private String assertionType;
    public static final String THRESHOLD_VALUE = "thresholdValue";
    private Dispatchable dispatcher = null;
    private AssertionValuePair counter;
    private boolean match = false;
    private long threshold = 0L, count = 0L;
    private IPatternParameter<E> threshParam = null;
    private SortedAccumulator<E> accumulator;

    public ThresholdCountPE() {
        accumulator = new SortedAccumulator<E>();
        counter = new AssertionValuePair(0, 0);
        setPatternType(PatternType.THRESHOLD_COUNT.getName());
    }

    public String getId() {
        if (assertionType != null) {
            id += assertionType;
        }
        return id;
    }

    public void setAssertion(String assertion) {
        this.assertionType = assertion;
    }

    public void setDispatcher(Dispatchable dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void processEvent(E event) {
        synchronized (this) {
            if (threshParam == null) {
                threshParam = parameters.get(0);
                threshold = (Long) threshParam.getValue();
                assertionType = (String) threshParam.getRelation();
                counter.threshold = threshold;
            }
            accumulator.processAt(event.getClass().getName(), (E) event);
            counter.value = count++;
            execPolicy("process");
        }
    }

    @Override
    public void output() {
        ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(assertionType);
        match = assertion.assertEvent(counter);
        TreeMap<Object, List<E>> map = null;
        if (match) {
            IMatchEventMap<E> matchEventMap = new MatchEventMap<E>(false);
            map = this.accumulator.getMap();
            List<E> matchList = map.firstEntry().getValue();
            for (E mEvent : matchList) {
                matchEventMap.put(mEvent.getClass().getName(), mEvent);
                getMatchingSet().add(mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, getOutputStreamName());
            accumulator.clear();
        }
        if (count > threshold) {
            if (accumulator.getSizeCount() > 0) {
                IUnMatchEventMap<E> unmatchEventMap = new UnMatchEventMap<E>(false);
                map = this.accumulator.getMap();
                List<E> unMatchList = map.firstEntry().getValue();
                for (E mEvent : unMatchList) {
                    unmatchEventMap.put(mEvent.getClass().getName(), mEvent);
                    getUnMatchedEventSet().add(mEvent);
                }
                publishUnMatchEvents(unmatchEventMap, dispatcher, getOutputStreamName());
            }
        }
    }

    @Override
    public void reset() {
        this.matchingSet.clear();
        this.participantEvents.clear();
        match = false;
        accumulator.clear();
        counter = new AssertionValuePair(0, 0);
        count = 0L;
    }
}
