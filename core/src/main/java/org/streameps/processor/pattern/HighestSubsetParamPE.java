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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import org.streameps.aggregation.collection.AssertionValuePair;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.core.comparator.AttributeValueEntry;
import org.streameps.core.util.SchemaUtil;
import org.streameps.dispatch.Dispatchable;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;
import org.streameps.processor.pattern.listener.UnMatchEventMap;

/**
 * The relative n highest values pattern is satisfied by the events which have
 * the n highest values of a specific attribute value compared over all the participant
 * events, where n is supplied as a pattern parameter.
 * 
 * @author Frank Appiah
 * @version 0.2.3
 */
public class HighestSubsetParamPE<E> extends BasePattern<E> {

    private SortedAccumulator<E> m_accumulator, u_accumulator;
    public static String HIGHEST_N_ATTR = "count";
    private int count = 0, paramCtrl = 0;
    private IPatternParameter paramCount, paramValue = null;
    private boolean match = false;
    private Dispatchable dispatcher = null;

    public HighestSubsetParamPE() {
        m_accumulator = new SortedAccumulator<E>();
        u_accumulator = new SortedAccumulator<E>();
        setPatternType(PatternType.HIGHEST_SUBSET_PARAM.getName());
    }

    @Override
    public void output() {
        IUnMatchEventMap<E> unmatchEventMap = new UnMatchEventMap<E>(false);
        IMatchEventMap<E> matchEventMap = new MatchEventMap<E>(false);

        paramValue = getParameters().get(paramCtrl);
        int i = 0;
        for (E event : this.participantEvents) {
            double value = (Double) SchemaUtil.getPropertyValue(event, paramValue.getPropertyName());
            ThresholdAssertion asserth = OperatorAssertionFactory.getAssertion(paramValue.getRelation());
            match = asserth.assertEvent(new AssertionValuePair((Double) paramValue.getValue(), value));
            if (match) {
                this.m_accumulator.processAt(event.getClass().getName() + i, event);
            } else {
                unmatchEventMap.put(event.getClass().getName(), event);
            }
            i++;
        }
        List<AttributeValueEntry<E>> attrValues = new LinkedList<AttributeValueEntry<E>>();
        TreeMap<Object, List<E>> m_map = this.m_accumulator.getMap();
        List<E> matchList = m_map.firstEntry().getValue();
        for (E event : matchList) {
            double value = (Double) SchemaUtil.getPropertyValue(event, paramValue.getPropertyName());
            attrValues.add(new AttributeValueEntry(event, value, AttributeValueEntry.CompareOrder.BOTTOM));
        }
        Collections.sort(attrValues);
        for (AttributeValueEntry<E> entry : attrValues) {
            if (i < count) {
                E mEvent = entry.getEvent();
                this.matchingSet.add(entry.getEvent());
                matchEventMap.put(mEvent.getClass().getName(), mEvent);
            } else {
                unmatchEventMap.put(entry.getEvent().getClass().getName(), entry.getEvent());
            }
            i++;
        }
        if (this.matchingSet.size() > 0) {
            publishMatchEvents(matchEventMap, dispatcher, getOutputStreamName());
        }
        if (unmatchEventMap.getUnMatchingEvents().size() > 0) {
            publishUnMatchEvents(unmatchEventMap, dispatcher, getOutputStreamName());
        }
    }

    public void processEvent(E event) {
        this.participantEvents.add(event);
        if (paramCount == null) {
            paramCount = this.parameters.get(0);
            if (paramCount.getPropertyName().equalsIgnoreCase(HIGHEST_N_ATTR)) {
                count = (Integer) paramCount.getValue();
                paramCtrl = 1;
            } else {
                paramCount = this.parameters.get(1);
                if (paramCount.getPropertyName().equalsIgnoreCase(HIGHEST_N_ATTR)) {
                    count = (Integer) paramCount.getValue();
                    paramCtrl = 0;
                }
            }
        }
        execPolicy("process");
    }

    public void setDispatcher(Dispatchable dispatcher) {
        this.dispatcher = dispatcher;
    }
}
