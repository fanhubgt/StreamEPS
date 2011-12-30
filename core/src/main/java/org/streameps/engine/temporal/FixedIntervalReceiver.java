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
import org.streameps.context.TemporalOrder;
import org.streameps.context.temporal.FixedIntervalContext;
import org.streameps.context.temporal.IFixedIntervalContext;
import org.streameps.context.temporal.IFixedIntervalContextParam;
import org.streameps.core.ISchedulableEvent;
import org.streameps.core.comparator.TemporalEventSorter;
import org.streameps.core.util.IDUtil;
import org.streameps.core.util.SchemaUtil;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.engine.AbstractEPSReceiver;
import org.streameps.engine.IReceiverContext;
import org.streameps.engine.IReceiverPair;
import org.streameps.engine.IRouterContext;
import org.streameps.engine.ISchedulableQueue;
import org.streameps.engine.IScheduleCallable;
import org.streameps.engine.SchedulableQueue;
import org.streameps.exception.PredicateException;

/**
 *
 * @author Frank Appiah
 */
public class FixedIntervalReceiver<E>
        extends AbstractEPSReceiver<IContextPartition<IFixedIntervalContext>, E>
        implements IScheduleCallable<E> {

    private ISchedulableQueue<E> schedulableQueue;
    private long periodicTimeStamp = 10;
    private int queueSize = 10;
    private TemporalEventSorter eventSorter;
    private String annotation = "Receiver:=FixedEventInterval;fixedIntervalTime:";
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private ArrayDeque<E> deque = new ArrayDeque<E>();
    private Map<Long, E> eventMap;
    private Map<String, E> eventIDMap;
    private boolean isExecutorStarted = false;

    public FixedIntervalReceiver() {
        super();
        schedulableQueue = new SchedulableQueue<E>(this, periodicTimeStamp, timeUnit);
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
    }

    public FixedIntervalReceiver(long periodicTimeStamp, TimeUnit timeUnit) {
        this.periodicTimeStamp = periodicTimeStamp;
        annotation += periodicTimeStamp;
        schedulableQueue = new SchedulableQueue<E>(this, periodicTimeStamp, queueSize, timeUnit);
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
    }

    public FixedIntervalReceiver(long periodicTimeStamp) {
        this.periodicTimeStamp = periodicTimeStamp;
        annotation += periodicTimeStamp;
        schedulableQueue = new SchedulableQueue<E>(this, periodicTimeStamp, queueSize, timeUnit);
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
    }

    public FixedIntervalReceiver(long periodicTimeStamp, int queue) {
        this.periodicTimeStamp = periodicTimeStamp;
        annotation += periodicTimeStamp;
        this.queueSize = queue;
        schedulableQueue = new SchedulableQueue<E>(this, periodicTimeStamp, queueSize, timeUnit);
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
    }

    public FixedIntervalReceiver(long periodicTimeStamp, int queue, TimeUnit timeUnit) {
        this.periodicTimeStamp = periodicTimeStamp;
        this.timeUnit = timeUnit;
        annotation += periodicTimeStamp;
        this.queueSize = queue;
        schedulableQueue = new SchedulableQueue<E>(this, periodicTimeStamp, queueSize, timeUnit);
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
    }

    public FixedIntervalReceiver(ISchedulableQueue schedulableQueue) {
        this.schedulableQueue = schedulableQueue;
        eventMap = new TreeMap<Long, E>();
        eventIDMap = new TreeMap<String, E>();
    }

    public void routeEvent(E event, IReceiverPair<? extends IRouterContext, ? extends IReceiverContext> receiverPair) {
    }

    @Override
    public void onReceive(E event) {
        if (!isExecutorStarted) {
            IDispatcherService service = getEventQueue().getDispatcherService();
            schedulableQueue.setDispatcherService(service);
            schedulableQueue.schedulePollAtRate();
            isExecutorStarted = true;
        }
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
        this.deque = deque;
        IContextPartition<IFixedIntervalContext> contextPartition = new ContextPartition<IFixedIntervalContext>();
        IPartitionWindow<ISortedAccumulator<E>> partitionWindow = new PartitionWindow<ISortedAccumulator<E>>();
        IFixedIntervalContextParam contextParam = (IFixedIntervalContextParam) receiverContext.getContextParam().getContextParameter();
        IFixedIntervalContext context = new FixedIntervalContext(IDUtil.getUniqueID(annotation),
                contextParam);
        context.setIdentifier(IDUtil.getUniqueID(new Date().toString()));
        context.setContextDimension(ContextDimType.TEMPORAL);
        ISortedAccumulator<E> accumulator = new SortedAccumulator<E>();
        String attribute = receiverContext.getAttribute();
        annotation += "attribute:" + attribute;

        if (receiverContext.getContextDetail().getContextDimension() == ContextDimType.TEMPORAL) {
            validate(contextParam.getOrdering(), attribute, accumulator);
        }
        partitionWindow.setAnnotation(toString());
        partitionWindow.setWindow(accumulator);
        contextPartition.getPartitionWindow().add(partitionWindow);
        getContextPartitions().add(contextPartition);

        // pushContextPartition(getContextPartitions());
    }

    private boolean validate(TemporalOrder temporalOrder, String attribute, ISortedAccumulator<E> accumulator) {
        switch (temporalOrder) {
            case DETECTION_TIME: {
                for (Long key : eventMap.keySet()) {
                    E event = eventMap.get(key);
                    E acEvent = deque.poll();
                    if (event.equals(acEvent)) {
                        if (validateInterval(getIntervalStart(), getIntervalEnd(), key)) {
                            accumulator.processAt(event.getClass().getName(), event);
                        }
                    }
                }
            }
            break;
            case OCCURENCE_TIME:
                evalAttribute(attribute, accumulator);
                break;
            case TEMPORAL_ATT:
                evalAttribute(attribute, accumulator);
                break;
            default:
                throw new PredicateException(attribute + " is not a property set in the object structure.");
        }
        return false;
    }

    private void evalAttribute(String attribute, ISortedAccumulator<E> accumulator) {
        for (E event : deque) {
            long timestamp = 0L;
            Object value = SchemaUtil.getProperty(event, attribute);
            if (value instanceof Date) {
                Date attDate = (Date) value;
                timestamp = attDate.getTime();
            } else if (value instanceof Long) {
                timestamp = (Long) value;
            } else {
                throw new PredicateException(attribute + " is not a property set in the object structure.");
            }
            if (validateInterval(getIntervalStart(), getIntervalEnd(), timestamp)) {
                accumulator.processAt(event.getClass().getName(), event);
            }
        }
    }

    public void buildContextPartition(IReceiverContext receiverContext) {
        buildTemporalPartition(receiverContext, deque);
    }

    public void onScheduleCall(List<ISchedulableEvent<E>> schedulableEvents) {
        long now = new Date().getTime();
        if (schedulableEvents != null) {
            for (ISchedulableEvent<E> schedulableEvent : schedulableEvents) {
                E evnt = schedulableEvent.getEvent();
                //deque.add(evnt);
                eventMap.put(now - schedulableEvent.getTimestamp(), evnt);
                eventIDMap.put(schedulableEvent.getIdentifier(), evnt);
                getEventQueue().addToQueue(evnt, evnt.getClass().getName());
            }
        }
    }

    public void setFixedIntervalTimeStamp(long fixedIntervalTimeStamp) {
        this.periodicTimeStamp = fixedIntervalTimeStamp;
    }

    public void setFixedIntervalTimeStamp(Date fixedIntervalTimeStamp) {
        this.periodicTimeStamp = fixedIntervalTimeStamp.getTime();
    }

    public long getPeriodicTimeStamp() {
        return periodicTimeStamp;
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
            return periodicTimeStamp = ((startInterval + endInterval) - startInterval);
        }
        return (periodicTimeStamp = endInterval - startInterval);
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

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public int getQueueSize() {
        return queueSize;
    }
}
