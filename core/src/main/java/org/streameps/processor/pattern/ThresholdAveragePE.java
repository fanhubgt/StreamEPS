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
import org.streameps.aggregation.AvgAggregation;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.core.util.SchemaUtil;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;

public class ThresholdAveragePE extends BasePattern {

    private static String THRESHOLD_NAME = "s4:thesholdavg:";
    private String assertionType;
    private SortedAccumulator accumulator;
    public static final String THRESHOLD_AVG_ATTR = "average";
    private Dispatcher dispatcher = null;
    private AggregateValue aggregateValue;
    private String outputStreamName = null;
    private PatternParameter threshParam = null;
    private AvgAggregation avgAggregation;
    private double avg_threshold =0.0;
    private String prop;
    private boolean match = false;

    public ThresholdAveragePE() {
        avgAggregation = new AvgAggregation();
        aggregateValue = new AggregateValue(0, 0);
       accumulator=new SortedAccumulator();
    }

    @Override
    public void output() {
        int temp = matchingSet.size();
        int tc = 0;
        if (matchingSet.size() > 0) {
            IMatchEventMap matchEventMap = new MatchEventMap(false);
            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.getClass().getName(), mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, outputStreamName);
            matchingSet.clear();
        }
    }

    public void processEvent(Object event) {
        synchronized (this) {
            if (threshParam == null) {
                threshParam = this.parameters.get(0);
                prop = threshParam.getPropertyName();
                avg_threshold = (Double) threshParam.getValue();
            }
            accumulator.processAt(event.getClass().getName(), event);
            assertionType = (String) threshParam.getRelation();
            avgAggregation.process(aggregateValue, (Double) (SchemaUtil.getPropertyValue(event, prop)));
            ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(assertionType);
            match = assertion.assertEvent(new AggregateValue(avg_threshold,avgAggregation.getValue()));
            if (match) {
                logResult(avgAggregation);
                for (Object mat_event :accumulator.getEventsByKey(event.getClass().getName())) {
                    this.matchingSet.add(mat_event);
                }
                execPolicy("process");
                accumulator.clear();
                match = false;
            }
        }
    }

    @Override
    public String getId() {
        return THRESHOLD_NAME + name;
    }

    public void setOutputStreamName(String outputStreamName) {
        this.outputStreamName = outputStreamName;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    private void logResult(AvgAggregation av)
    {
       System.out.println("Avg:"+av.getValue());
    }
}
