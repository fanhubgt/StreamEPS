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

import org.streameps.aggregation.AggregateValue;
import org.streameps.aggregation.AvgAggregation;
import org.streameps.core.util.SchemaUtil;
import org.streameps.dispatch.Dispatchable;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;
import org.streameps.processor.pattern.listener.UnMatchEventMap;

/**
 * The value average pattern is satisfied when the value of a specific attribute,
 * averaged over all the participant events, satisfies the value average threshold
 * assertion.
 * @since 0.1
 * @version 0.2.2
 *
 * @author Frank Appiah
 */
public class ThresholdAveragePE extends BasePattern {

    private String assertionType;
    private Dispatchable dispatcher = null;
    private AggregateValue aggregateValue;
    private IPatternParameter threshParam = null;
    private AvgAggregation avgAggregation;
    private double avg_threshold = 0.0;
    private String propertyName;
    private boolean match = false;

    public ThresholdAveragePE() {
        avgAggregation = new AvgAggregation();
        aggregateValue = new AggregateValue(0, 0);
         setPatternType(PatternType.THRESHOLD_AVG.getName());
    }

    @Override
    public void output() {
        for (Object event : this.participantEvents) {
            avgAggregation.process(aggregateValue, (Double) (SchemaUtil.getPropertyValue(event, propertyName)));
        }
        ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(assertionType);
        match = assertion.assertEvent(new AggregateValue(avg_threshold, avgAggregation.getValue()));
        if (match) {
            if (execPolicy("output")) {
                this.matchingSet.addAll(participantEvents);
            }
        }
        if (matchingSet.size() > 0) {
            IMatchEventMap matchEventMap = new MatchEventMap(false);
            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.getClass().getName(), postProcessBeforeSend(mEvent));
            }
            publishMatchEvents(matchEventMap, dispatcher, getOutputStreamName());
            matchingSet.clear();
        } else if (!execPolicy("output")) {
            IUnMatchEventMap unmatchEventMap = new UnMatchEventMap(false);
            for (Object mEvent : this.participantEvents) {
                unmatchEventMap.put(mEvent.getClass().getName(), postProcessBeforeSend(mEvent));
            }
            publishUnMatchEvents(unmatchEventMap, dispatcher, getOutputStreamName());
        }
    }

    public void processEvent(Object event) {
        synchronized (this) {
            if (threshParam == null) {
                threshParam = this.parameters.get(0);
                propertyName = threshParam.getPropertyName();
                avg_threshold = (Double) threshParam.getValue();
                assertionType = (String) threshParam.getRelation();
            }
            this.participantEvents.add(preProcessOnRecieve(event));
            execPolicy("process");
        }
    }

    public void setDispatcher(Dispatchable dispatcher) {
        this.dispatcher = dispatcher;
    }
}
