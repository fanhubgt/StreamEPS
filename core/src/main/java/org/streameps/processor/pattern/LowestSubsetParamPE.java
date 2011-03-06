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
import org.streameps.aggregation.AggregateValue;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.core.util.SchemaUtil;
import org.streameps.dispatch.Dispatchable;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;
import org.streameps.processor.pattern.listener.UnMatchEventMap;

/**
 *
 * @author Frank Appiah
 */
public class LowestSubsetParamPE extends BasePattern{

    public static String SUBSET_NAME = "s4:lowest_param";
    public static String LOWEST_N_ATTR = "count";
    private SortedAccumulator m_accumulator, u_accumulator;
    private int count = 0, paramCtrl = 0;
    private PatternParameter paramCount, paramValue = null;
    private boolean match = false;
    private Dispatchable dispatcher = null;

    public LowestSubsetParamPE() {
        m_accumulator = new SortedAccumulator();
        u_accumulator = new SortedAccumulator();
    }

    @Override
    public void output() {

        paramValue = getParameters().get(paramCtrl);
        int i = 0;
        for (Object event : this.participantEvents) {
            double value = (Double) SchemaUtil.getPropertyValue(event, paramValue.getPropertyName());
            ThresholdAssertion asserth = OperatorAssertionFactory.getAssertion(paramValue.getRelation());
            match = asserth.assertEvent(new AggregateValue((Double) paramValue.getValue(), value));
            if (match) {
                this.m_accumulator.processAt(event.getClass().getName() + i, event);
            } else {
                this.u_accumulator.processAt(event.getClass().getName(), event);
            }
            i++;
        }
        this.matchingSet.addAll(m_accumulator.lowest(count));
        if (this.matchingSet.size() > 0) {
            IMatchEventMap matchEventMap = new MatchEventMap(false);

            for (Object mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.getClass().getName(), mEvent);

            }
            publishMatchEvents(matchEventMap, dispatcher, getOutputStreamName());
            matchingSet.clear();
        }
        if (u_accumulator.totalCount() > 0) {
            IUnMatchEventMap unmatchEventMap = new UnMatchEventMap(false);
            TreeMap<Object, List<Object>> map = this.u_accumulator.getMap();
            List<Object> unMatchList = map.firstEntry().getValue();
            for (Object mEvent : unMatchList) {
                unmatchEventMap.put(mEvent.getClass().getName(), mEvent);
            }
            publishUnMatchEvents(unmatchEventMap, dispatcher, getOutputStreamName());
        }
    }

    public void processEvent(Object event) {
        this.participantEvents.add(event);
        if (paramCount == null) {
            paramCount = this.parameters.get(0);
            if (paramCount.getPropertyName().equalsIgnoreCase(LOWEST_N_ATTR)) {
                count = (Integer) paramCount.getValue();
                paramCtrl = 1;
            } else {
                paramCount = this.parameters.get(1);
                if (paramCount.getPropertyName().equalsIgnoreCase(LOWEST_N_ATTR)) {
                    count = (Integer) paramCount.getValue();
                    paramCtrl = 0;
                }
            }
        }
        execPolicy("process");
    }

    /**
     * @param dispatcher
     *            the dispatcher to set
     */
    public void setDispatcher(Dispatchable dispatcher) {
        this.dispatcher = dispatcher;
    }

}
