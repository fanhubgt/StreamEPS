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
package org.streameps.engine.temporal;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.List;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.aggregation.collection.ITemporalWindow;
import org.streameps.aggregation.collection.TemporalWindow;
import org.streameps.context.ContextDimType;
import org.streameps.context.ContextPartition;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextPartition;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.PartitionWindow;
import org.streameps.context.temporal.FixedIntervalContext;
import org.streameps.context.temporal.IFixedIntervalContext;
import org.streameps.context.temporal.IFixedIntervalContextParam;
import org.streameps.core.comparator.TemporalEventSorter;
import org.streameps.core.util.IDUtil;
import org.streameps.core.util.RuntimeUtil;
import org.streameps.core.util.SchemaUtil;
import org.streameps.engine.AbstractEPSReceiver;
import org.streameps.engine.IReceiverContext;
import org.streameps.engine.IReceiverPair;
import org.streameps.engine.IRouterContext;

/**
 *
 * @author Frank Appiah
 */
public class FixedEventIntervalReceiver<E> extends AbstractEPSReceiver<IContextPartition<IFixedIntervalContext>, E> {

    private ITemporalWindow<E> temporalWindow;
    private long fixedIntervalTimeStamp;
    private TemporalEventSorter eventSorter;
    private String annotation = "Receiver:=FixedEventInterval;fixedIntervalTime:";
    private String attribute;

    public FixedEventIntervalReceiver() {
        temporalWindow = new TemporalWindow();
    }

    public FixedEventIntervalReceiver(ITemporalWindow temporalWindow, long fixedIntervalTimeStamp) {
        this.temporalWindow = temporalWindow;
        this.fixedIntervalTimeStamp = fixedIntervalTimeStamp;
        annotation += fixedIntervalTimeStamp;
    }

    public FixedEventIntervalReceiver(long fixedIntervalTimeStamp) {
        this.fixedIntervalTimeStamp = fixedIntervalTimeStamp;
        annotation += fixedIntervalTimeStamp;
    }

    public FixedEventIntervalReceiver(long fixedIntervalTimeStamp, String attribute) {
        this.fixedIntervalTimeStamp = fixedIntervalTimeStamp;
        annotation += fixedIntervalTimeStamp;
        this.attribute = attribute;
        annotation += ";attribute:" + attribute;
    }

    public FixedEventIntervalReceiver(ITemporalWindow temporalWindow) {
        this.temporalWindow = temporalWindow;
    }

    public void routeEvent(E event, IReceiverPair<? extends IRouterContext, ? extends IReceiverContext> receiverPair) {
    }

    @Override
    public void onReceive(E event) {
        super.onReceive(event);
        temporalWindow.putOrUpdate(RuntimeUtil.getTimestamp(getIntervalStart()), event);
    }

    private long getIntervalStart() {
        IReceiverContext receiverContext = getReceiverContext();
        IFixedIntervalContextParam contextParam = (IFixedIntervalContextParam) receiverContext.getContextParam();
        return contextParam.getIntervalStart();
    }

    private long getIntervalEnd() {
        IReceiverContext receiverContext = getReceiverContext();
        IFixedIntervalContextParam contextParam = (IFixedIntervalContextParam) receiverContext.getContextParam();
        return contextParam.getIntervalEnd();
    }

    public void buildContextPartition(IReceiverContext receiverContext, List<E> events) {
        setReceiverContext(receiverContext);
        IContextDetail contextDetail = receiverContext.getContextDetail();

        IFixedIntervalContextParam contextParam = (IFixedIntervalContextParam) receiverContext.getContextParam();
        ArrayDeque<E> deque = new ArrayDeque<E>();
        if (events == null) {
            deque = temporalWindow.getWindowEvents(contextParam.getIntervalEnd());
        } else {
            for (E event : events) {
                deque.add(event);
            }
        }
        buildTemporalPartition(receiverContext, deque);
    }

    private void buildTemporalPartition(IReceiverContext receiverContext, ArrayDeque<E> deque) {
        IContextPartition<IFixedIntervalContext> contextPartition = new ContextPartition<IFixedIntervalContext>();
        IPartitionWindow<ISortedAccumulator<E>> partitionWindow = new PartitionWindow<ISortedAccumulator<E>>();
        IFixedIntervalContext context = new FixedIntervalContext(IDUtil.getUniqueID(annotation),
                (IFixedIntervalContextParam) receiverContext.getContextParam());
        context.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        context.setContextDimension(ContextDimType.TEMPORAL);

        if (receiverContext.getContextDetail().getContextDimension() == ContextDimType.TEMPORAL) {
            for (E event : deque) {
                long timestamp = 0L;
                Object value = SchemaUtil.getProperty(event, getAttribute());
                if (value instanceof Date) {
                    Date attDate = (Date) value;
                    timestamp = attDate.getTime();
                } else if (value instanceof Long) {
                    timestamp = (Long) value;
                }
                setFixedIntervalTimeStamp(timestamp);
                if (validateInterval(getIntervalStart(), getIntervalEnd())) {
                    partitionWindow.getWindow().processAt(event.getClass().getName(), event);
                }
            }
        }
        partitionWindow.setAnnotation(toString());
        contextPartition.getPartitionWindow().add(partitionWindow);
        getContextPartitions().add(contextPartition);
    }

    public void buildContextPartition(IReceiverContext receiverContext) {
        IFixedIntervalContextParam contextParam = (IFixedIntervalContextParam) receiverContext.getContextParam();
        ArrayDeque<E> deque = temporalWindow.getWindowEvents(contextParam.getIntervalEnd());
        buildTemporalPartition(receiverContext, deque);
    }

    public void setFixedIntervalTimeStamp(long fixedIntervalTimeStamp) {
        this.fixedIntervalTimeStamp = fixedIntervalTimeStamp;
    }

    public void setFixedIntervalTimeStamp(Date fixedIntervalTimeStamp) {
        this.fixedIntervalTimeStamp = fixedIntervalTimeStamp.getTime();
    }

    public long getFixedIntervalTimeStamp() {
        return fixedIntervalTimeStamp;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public ITemporalWindow<E> getTemporalWindow() {
        return temporalWindow;
    }

    public String getAttribute() {
        return attribute;
    }

    public boolean validateInterval(long startInterval, long endInterval) {
        if (getFixedIntervalTimeStamp() >= startInterval && getFixedIntervalTimeStamp() < endInterval) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return annotation;
    }
}
