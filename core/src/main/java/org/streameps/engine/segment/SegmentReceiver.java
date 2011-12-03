/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2011.
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
 * 
 *  =============================================================================
 */
package org.streameps.engine.segment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.context.ContextDimType;
import org.streameps.context.ContextPartition;
import org.streameps.context.IContextPartition;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.IPredicateExpr;
import org.streameps.context.IPredicateTerm;
import org.streameps.context.PartitionWindow;
import org.streameps.context.segment.ISegmentContext;
import org.streameps.context.segment.ISegmentParam;
import org.streameps.context.segment.SegmentContext;
import org.streameps.core.util.IDUtil;
import org.streameps.core.util.SchemaUtil;
import org.streameps.engine.AbstractEPSReceiver;
import org.streameps.engine.IReceiverContext;
import org.streameps.engine.IReceiverPair;
import org.streameps.engine.IRouterContext;

/**
 *
 * @author Frank Appiah
 */
public class SegmentReceiver<E> extends AbstractEPSReceiver<IContextPartition<ISegmentContext>, E> {

    private String annotation = "Receiver:=Segment;predicatedEnabled:";

    public void routeEvent(E event,
            IReceiverPair<? extends IRouterContext, ? extends IReceiverContext> receiverPair) {
        //todo: implement segment router in receiver.
    }

    public void buildContextPartition(IReceiverContext receiverContext) {
        ISortedAccumulator<E> accumulator = getEventQueue().getAccumulator();
        List<E> events = new ArrayList<E>();
        for (Object key : accumulator.getMap().keySet()) {
            for (E event : accumulator.getAccumulatedByKey(key)) {
                events.add(event);
            }
        }
        buildContextPartition(receiverContext, events);
    }

    public void buildContextPartition(IReceiverContext receiverContext, List<E> events) {
        ISegmentParam segmentParam = (ISegmentParam) receiverContext.getContextParam().getContextParameter();

        IContextPartition<ISegmentContext> contextPartition = new ContextPartition<ISegmentContext>
                (IDUtil.getUniqueID(new Date().toString()));

        ISegmentContext context = new SegmentContext(IDUtil.getUniqueID(annotation),
                segmentParam, IDUtil.getUniqueID(new Date().toString()));
        contextPartition.setContext(context);
        contextPartition.getContext().setContextParameter(segmentParam);
        IPartitionWindow<ISortedAccumulator<E>> partitionWindow = new PartitionWindow<ISortedAccumulator<E>>();
        partitionWindow.setWindow(new SortedAccumulator<E>());
        if (receiverContext.getContextDetail().getContextDimension() == ContextDimType.SEGMENT_ORIENTED) {
            boolean satisfied, isPredicatedEnabled = segmentParam.isPredicateEnabled();
            annotation += isPredicatedEnabled+";";
            for (E event : events) {
                if (!isPredicatedEnabled) {
                    satisfied = verifyAttributes(event, segmentParam.getAttributes());
                } else {
                    satisfied = validatePredicates(event,
                            segmentParam.getPartitionExpr(),
                            receiverContext.getPredicateTerm());
                }
                if (satisfied) {
                    String key = event.getClass().getName();
                    partitionWindow.getWindow().processAt(key, event);
                }
            }
        }
        annotation += receiverContext.getPredicateTerm().toString();
        partitionWindow.setAnnotation(annotation);
        contextPartition.getPartitionWindow().add(partitionWindow);
        getContextPartitions().add(contextPartition);

        pushContextPartition(getContextPartitions());

    }

    public boolean verifyAttributes(E event, List<String> attributes) {
        int verified = 0;
        for (String attribute : attributes) {
            Object result = SchemaUtil.getPropertyValue(event, attribute);
            if (result != null) {
                verified += 1;
            }
        }
        return (verified == attributes.size());
    }

    private boolean validatePredicates(E event, List<IPredicateExpr> predicateExprs, IPredicateTerm predicateTerm) {
        boolean validated = false;
        for (IPredicateExpr<E> expr : predicateExprs) {
            validated |= expr.evalExpr(event, predicateTerm);
        }
        return validated;
    }

    @Override
    public String toString() {
        return annotation;
    }
}
