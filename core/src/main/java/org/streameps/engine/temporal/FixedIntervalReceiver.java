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
import java.util.concurrent.TimeUnit;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.context.ContextDimType;
import org.streameps.context.ContextPartition;
import org.streameps.context.IContextPartition;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.PartitionWindow;
import org.streameps.context.temporal.FixedIntervalContext;
import org.streameps.context.temporal.IFixedIntervalContext;
import org.streameps.context.temporal.IFixedIntervalContextParam;
import org.streameps.core.ISchedulableEvent;
import org.streameps.core.comparator.TemporalEventSorter;
import org.streameps.util.IDUtil;
import org.streameps.core.util.SchemaUtil;
import org.streameps.engine.AbstractEPSReceiver;
import org.streameps.engine.IReceiverContext;
import org.streameps.engine.IReceiverPair;
import org.streameps.engine.IRouterContext;
import org.streameps.engine.ISchedulableQueue;
import org.streameps.engine.IScheduleCallable;
import org.streameps.engine.SchedulableQueue;

/**
 *
 * @author Frank Appiah
 */
public class FixedIntervalReceiver<E>
        extends AbstractEPSReceiver<IContextPartition<IFixedIntervalContext>, E>
        implements IScheduleCallable<E> {

    private ISchedulableQueue<E> schedulableQueue;
    private long fixedIntervalTimeStamp = 10;
    private TemporalEventSorter eventSorter;
    private String annotation = "Receiver:=FixedEventInterval;fixedIntervalTime:";
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private ArrayDeque<E> deque = new ArrayDeque<E>();

    public FixedIntervalReceiver() {
        super();
        schedulableQueue = new SchedulableQueue<E>(this, fixedIntervalTimeStamp, timeUnit);
    }

    public FixedIntervalReceiver(ISchedulableQueue schedulableQueue, long fixedIntervalTimeStamp) {
        this.schedulableQueue = schedulableQueue;
        this.fixedIntervalTimeStamp = fixedIntervalTimeStamp;
        annotation += fixedIntervalTimeStamp;
        schedulableQueue = new SchedulableQueue<E>(this, fixedIntervalTimeStamp, timeUnit);
    }

    public FixedIntervalReceiver(long fixedIntervalTimeStamp) {
        this.fixedIntervalTimeStamp = fixedIntervalTimeStamp;
        annotation += fixedIntervalTimeStamp;
        schedulableQueue = new SchedulableQueue<E>(this, fixedIntervalTimeStamp, timeUnit);
    }

    public FixedIntervalReceiver(ISchedulableQueue schedulableQueue) {
        this.schedulableQueue = schedulableQueue;
    }

    public void routeEvent(E event, IReceiverPair<? extends IRouterContext, ? extends IReceiverContext> receiverPair) {
    }

    @Override
    public void onReceive(E event) {
        schedulableQueue.addToQueue(event);
    }

    private long getIntervalStart() {
        IReceiverContext receiverContext = getReceiverContext();
        IFixedIntervalContextParam contextParam = (IFixedIntervalContextParam) receiverContext.getContextParam().getContextParameter();
        return contextParam.getIntervalStart();
    }

    private long getIntervalEnd() {
        IReceiverContext receiverContext = getReceiverContext();
        IFixedIntervalContextParam contextParam = (IFixedIntervalContextParam) receiverContext.getContextParam().getContextParameter();
        return contextParam.getIntervalEnd();
    }

    public void buildContextPartition(IReceiverContext receiverContext, List<E> events) {
        setReceiverContext(receiverContext);
        for (E event : events) {
            deque.add(event);
        }
        buildTemporalPartition(receiverContext, deque);
    }

    private void buildTemporalPartition(IReceiverContext receiverContext, ArrayDeque<E> deque) {
        IContextPartition<IFixedIntervalContext> contextPartition = new ContextPartition<IFixedIntervalContext>();
        IPartitionWindow<ISortedAccumulator<E>> partitionWindow = new PartitionWindow<ISortedAccumulator<E>>();
        IFixedIntervalContext context = new FixedIntervalContext(IDUtil.getUniqueID(annotation),
                (IFixedIntervalContextParam) receiverContext.getContextParam().getContextParameter());
        context.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        context.setContextDimension(ContextDimType.TEMPORAL);
        ISortedAccumulator<E> accumulator = new SortedAccumulator<E>();
        String attribute = receiverContext.getAttribute();
        annotation += "attribute:" + attribute;
        
        if (receiverContext.getContextDetail().getContextDimension() == ContextDimType.TEMPORAL) {
            for (E event : deque) {
                long timestamp = 0L;
                Object value = SchemaUtil.getProperty(event, attribute);
                if (value instanceof Date) {
                    Date attDate = (Date) value;
                    timestamp = attDate.getTime();
                } else if (value instanceof Long) {
                    timestamp = (Long) value;
                }
                if (validateInterval(getIntervalStart(), getIntervalEnd(), timestamp)) {
                    accumulator.processAt(event.getClass().getName(), event);
                }
            }
        }
        partitionWindow.setAnnotation(toString());
        partitionWindow.setWindow(accumulator);
        contextPartition.getPartitionWindow().add(partitionWindow);
        getContextPartitions().add(contextPartition);

        pushContextPartition(getContextPartitions());
    }

    public void buildContextPartition(IReceiverContext receiverContext) {
        buildTemporalPartition(receiverContext, deque);
    }

    public void onScheduleCall(List<ISchedulableEvent<E>> schedulableEvents) {
        for (ISchedulableEvent<E> schedulableEvent : schedulableEvents) {
            E evnt = schedulableEvent.getEvent();
            deque.add(evnt);
        }
        buildContextPartition(getReceiverContext());
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

    public boolean validateInterval(long startInterval, long endInterval, long fixedPeriod) {
        if (fixedPeriod >= startInterval && fixedPeriod < endInterval) {
            return true;
        } else {
            return false;
        }
    }

    public long calculatePeriod(long startInterval, long endInterval) {
        if (startInterval > endInterval) {
            return fixedIntervalTimeStamp = ((startInterval + endInterval) - startInterval);
        }
        return (fixedIntervalTimeStamp = endInterval - startInterval);
    }

    @Override
    public String toString() {
        return annotation;
    }

    public ISchedulableQueue getSchedulableQueue() {
        return schedulableQueue;
    }

    public void setSchedulableQueue(ISchedulableQueue schedulableQueue) {
        this.schedulableQueue = schedulableQueue;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
