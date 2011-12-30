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
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.context.ContextDimType;
import org.streameps.context.ContextPartition;
import org.streameps.context.IContextPartition;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.PartitionWindow;
import org.streameps.context.temporal.ISlidingFixedIntervalContext;
import org.streameps.context.temporal.ISlidingFixedIntervalParam;
import org.streameps.context.temporal.SlidingFixedIntervalContext;
import org.streameps.core.ISchedulableEvent;
import org.streameps.core.util.IDUtil;
import org.streameps.dispatch.IDispatcherService;
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
public class SlidingFixedIntervalReceiver<E> extends AbstractEPSReceiver<IContextPartition<ISlidingFixedIntervalContext>, E>
        implements IScheduleCallable<E> {

    private boolean isExecutorStarted = false;
    private ISlidingFixedIntervalParam fixedSlidingParam;
    private int eventCount = 10;
    private int eventInterval = 10;
    private List<ISchedulableEvent<E>> schedulableEvents;
    private long duration = 10;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private ISchedulableQueue schedulableQueue;
    private ArrayDeque<E> deque = new ArrayDeque<E>();
    private Map<Long, E> eventMap;
    private Map<String, E> eventIDMap;

    public SlidingFixedIntervalReceiver() {
        super();
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
    }

    public SlidingFixedIntervalReceiver(int eventCount, int eventInterval, long duration) {
        super();
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
        this.eventCount = eventCount;
        this.eventInterval = eventInterval;
        this.duration = duration;
    }

    public void routeEvent(E event, IReceiverPair<? extends IRouterContext, ? extends IReceiverContext> receiverPair) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onReceive(E event) {
        if (!isExecutorStarted) {
            startQueue();
        }
        schedulableQueue.addToQueue(event);
    }

    private void startQueue() {
        fixedSlidingParam = (ISlidingFixedIntervalParam) getReceiverContext().getContextParam().getContextParameter();
        eventCount = (int) (long) fixedSlidingParam.getIntervalSize();
        eventInterval = (int) (long) fixedSlidingParam.getIntervalPeriod();
        duration = fixedSlidingParam.getIntervalDuration();

        getEventQueue().setQueueSize(eventCount);
        schedulableQueue = new SchedulableQueue(this, eventInterval, eventCount, duration, timeUnit);

        IDispatcherService service = getEventQueue().getDispatcherService();
        schedulableQueue.setDispatcherService(service);
        schedulableQueue.schedulePollAtRateByCount();
        isExecutorStarted = true;
    }

    public void buildContextPartition(IReceiverContext receiverContext, List<E> events) {
        for (E event : events) {
            deque.add(event);
        }
        buildContextPartition(receiverContext);
    }

    public void buildContextPartition(IReceiverContext receiverContext) {
        IContextPartition<ISlidingFixedIntervalContext> contextPartition = new ContextPartition<ISlidingFixedIntervalContext>();
        IPartitionWindow<ISortedAccumulator<E>> partitionWindow = new PartitionWindow<ISortedAccumulator<E>>();

        ISlidingFixedIntervalContext context = new SlidingFixedIntervalContext();
        context.setContextParameter(fixedSlidingParam);
        context.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        context.setContextDimension(ContextDimType.TEMPORAL);

        ISortedAccumulator<E> accumulator = new SortedAccumulator<E>();

        if (receiverContext.getContextDetail().getContextDimension() == ContextDimType.TEMPORAL) {
            for (E event : deque) {
                accumulator.processAt(event.getClass().getName(), event);
            }
        }
        partitionWindow.setAnnotation(toString());
        partitionWindow.setWindow(accumulator);
        contextPartition.getPartitionWindow().add(partitionWindow);
        getContextPartitions().add(contextPartition);
    }

    public void onScheduleCall(List<ISchedulableEvent<E>> schedulableEvents) {
        this.schedulableEvents = schedulableEvents;
        long now = new Date().getTime();
        if (schedulableEvents != null) {
            for (ISchedulableEvent<E> event : schedulableEvents) {
                E evnt = event.getEvent();
                getEventQueue().addToQueue(evnt, evnt.getClass().getName());
                eventMap.put(now - event.getTimestamp(), evnt);
                eventIDMap.put(event.getIdentifier(), evnt);
            }
        }
    }

    public void setSchedulableQueue(ISchedulableQueue schedulableQueue) {
        this.schedulableQueue = schedulableQueue;
    }

    public ISchedulableQueue getSchedulableQueue() {
        return schedulableQueue;
    }

    public void setDeque(ArrayDeque<E> deque) {
        this.deque = deque;
    }

    public ArrayDeque<E> getDeque() {
        return deque;
    }
}
