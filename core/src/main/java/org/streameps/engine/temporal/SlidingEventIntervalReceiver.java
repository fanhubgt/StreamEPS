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
import org.streameps.context.temporal.IInitiatorEventList;
import org.streameps.context.temporal.ISlidingEventIntervalContext;
import org.streameps.context.temporal.ISlidingEventIntervalParam;
import org.streameps.context.temporal.SlidingEventIntervalContext;
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
import org.streameps.engine.temporal.validator.IValidatorContext;
import org.streameps.engine.temporal.validator.IInitiatorEventValidator;

/**
 *
 * @author Frank Appiah
 */
public class SlidingEventIntervalReceiver<E>
        extends AbstractEPSReceiver<IContextPartition<ISlidingEventIntervalContext>, E>
        implements IScheduleCallable<E> {

    private IInitiatorEventValidator eventValidator;
    private IValidatorContext validatorContext;
    private ISchedulableQueue schedulableQueue;
    private List<ISchedulableEvent<E>> schedulableEvents;
    private ISlidingEventIntervalParam slidingParam;
    private int eventCount = 10, eventInterval = 10;
    private long periodicDelay = 1;
    private boolean isExecutorStarted = false;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private IInitiatorEventList eventList;
    private ArrayDeque<E> deque = new ArrayDeque<E>();
    private Map<Long, E> eventMap;
    private Map<String, E> eventIDMap;

    public SlidingEventIntervalReceiver() {
        super();
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
    }

    public SlidingEventIntervalReceiver(IValidatorContext validatorContext) {
        super();
        this.validatorContext = validatorContext;
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
    }

    public SlidingEventIntervalReceiver(IValidatorContext validatorContext, ISchedulableQueue schedulableQueue) {
        super();
        this.validatorContext = validatorContext;
        this.schedulableQueue = schedulableQueue;
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
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
        slidingParam = (ISlidingEventIntervalParam) getReceiverContext().getContextParam().getContextParameter();

        eventInterval = (int) (long) slidingParam.getIntervalSize();
        getEventQueue().setQueueSize(eventInterval);
        
        eventCount = (int) (long) slidingParam.getEventPeriod();
        schedulableQueue = new SchedulableQueue(this, periodicDelay, eventCount, timeUnit);

        IDispatcherService service = getEventQueue().getDispatcherService();
        schedulableQueue.setDispatcherService(service);
        schedulableQueue.schedulePollAtRateByCount();
        isExecutorStarted = true;
    }

    public void buildContextPartition(IReceiverContext receiverContext, List<E> events) {
        slidingParam = (ISlidingEventIntervalParam) receiverContext.getContextParam().getContextParameter();
        eventList = slidingParam.getEventList();
        for (E event : events) {
            validatorContext.setEvent(event);
            if (eventValidator.validate(eventList, validatorContext)) {
                deque.add(event);
            }
        }
        buildContextPartition(receiverContext);
    }

    public void buildContextPartition(IReceiverContext receiverContext) {
        IContextPartition<ISlidingEventIntervalContext> contextPartition = new ContextPartition<ISlidingEventIntervalContext>();
        IPartitionWindow<ISortedAccumulator<E>> partitionWindow = new PartitionWindow<ISortedAccumulator<E>>();

        ISlidingEventIntervalContext context = new SlidingEventIntervalContext();
        context.setContextParameter(slidingParam);
        context.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        context.setContextDimension(ContextDimType.TEMPORAL_ORIENTED);

        ISortedAccumulator<E> accumulator = new SortedAccumulator<E>();

        if (receiverContext.getContextDetail().getContextDimension() == ContextDimType.TEMPORAL_ORIENTED) {
            for (E event : deque) {
                accumulator.processAt(event.getClass().getName(), event);
            }
        }
        partitionWindow.setAnnotation(toString());
        partitionWindow.setWindow(accumulator);
        contextPartition.getPartitionWindow().add(partitionWindow);
        getContextPartitions().add(contextPartition);
    }

    public IValidatorContext getValidatorContext() {
        return validatorContext;
    }

    public void setValidatorContext(IValidatorContext validatorContext) {
        this.validatorContext = validatorContext;
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

    public void setSchedulableQueue(ISchedulableQueue<E> schedulableQueue) {
        this.schedulableQueue = schedulableQueue;
    }

    public ISchedulableQueue<E> getSchedulableQueue() {
        return schedulableQueue;
    }
}
