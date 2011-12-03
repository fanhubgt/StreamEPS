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
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.core.comparator.AttributeValueEntry;
import org.streameps.core.util.SchemaUtil;
import org.streameps.dispatch.Dispatchable;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;
import org.streameps.processor.pattern.listener.UnMatchEventMap;

public class LowestSubsetPE<E> extends BasePattern<E> {

    public static String SUBSET_NAME = "eps:lowest";
    public static String LOWEST_N_ATTR = "count";
    private SortedAccumulator<E> m_accumulator, u_accumulator;
    private int count = 0, paramCtrl = 0;
    private IPatternParameter<E> paramCount = null;
    private boolean match = false;
    private Dispatchable dispatcher = null;

    public LowestSubsetPE() {
        m_accumulator = new SortedAccumulator<E>();
        u_accumulator = new SortedAccumulator<E>();
        setPatternType(PatternType.LOWEST_SUBSET.getName());
    }

    @Override
    public void output() {
        int i = 0;
        IUnMatchEventMap<E> unmatchEventMap = new UnMatchEventMap<E>(false);
        List<AttributeValueEntry<E>> attrValues = new LinkedList<AttributeValueEntry<E>>();
        for (E event : this.participantEvents) {
            double value = (Double) SchemaUtil.getPropertyValue(event, paramCount.getPropertyName());
            attrValues.add(new AttributeValueEntry<E>(event, value, AttributeValueEntry.CompareOrder.TOP));
        }
        Collections.sort(attrValues);
        for (AttributeValueEntry<E> entry : attrValues) {
            m_accumulator.processAt(entry.getEvent().getClass().getName(), entry.getEvent());
            i++;
            if (i > count) {
                unmatchEventMap.put(entry.getEvent().getClass().getName(), entry.getEvent());
            }
        }
        //Arrays.sort(null);
        this.matchingSet.addAll(m_accumulator.lowest(count));
        if (this.matchingSet.size() > 0) {
            IMatchEventMap<E> matchEventMap = new MatchEventMap<E>(false);
            for (E mEvent : this.matchingSet) {
                matchEventMap.put(mEvent.getClass().getName(), mEvent);
            }
            publishMatchEvents(matchEventMap, dispatcher, getOutputStreamName());
        }
        if (m_accumulator.getSizeCount() > count) {
            publishUnMatchEvents(unmatchEventMap, dispatcher, getOutputStreamName());
        }
    }

    public void processEvent(E event) {
        this.participantEvents.add(event);
        if (paramCount == null) {
            paramCount = this.parameters.get(0);
            count = (Integer) paramCount.getValue();
            paramCtrl = 1;

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
