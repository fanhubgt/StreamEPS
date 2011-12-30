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
package org.streameps.engine;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.streameps.aggregation.collection.ITemporalWindow;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.aggregation.collection.TemporalWindow;
import org.streameps.core.ISchedulableEvent;
import org.streameps.core.SchedulableEvent;
import org.streameps.core.util.IDUtil;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.thread.IEPSExecutorManager;
import org.streameps.thread.IWorkerCallable;

/**
 *
 * @author Frank Appiah
 */
public class SchedulableQueue<E> implements ISchedulableQueue<E> {

    private List<ISchedulableEvent<E>> scheduledEvents;
    private SoftReference<SortedAccumulator> accumulatorRef;
    private ISchedulableEvent<E> lastEvent;
    private WeakReference<IDispatcherService> dispatcherService;
    private ITemporalWindow<ISchedulableEvent<E>> temporalWindow;
    private IScheduleCallable callable = null;
    private long periodicDelay = 10;
    private int count = 10;
    private long duration = 0;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    public SchedulableQueue() {
        temporalWindow = new TemporalWindow<ISchedulableEvent<E>>();
        scheduledEvents = new ArrayList<ISchedulableEvent<E>>();
    }

    public SchedulableQueue(IScheduleCallable scheduleCallable) {
        this.callable = scheduleCallable;
        scheduledEvents = new ArrayList<ISchedulableEvent<E>>();
        temporalWindow = new TemporalWindow<ISchedulableEvent<E>>();
    }

    public SchedulableQueue(IScheduleCallable scheduleCallable, long period, int count) {
        this.callable = scheduleCallable;
        this.periodicDelay = period;
        this.count = count;
        temporalWindow = new TemporalWindow<ISchedulableEvent<E>>();
        scheduledEvents = new ArrayList<ISchedulableEvent<E>>();
    }

    public SchedulableQueue(IScheduleCallable scheduleCallable, long period, int count, TimeUnit timeUnit) {
        this.callable = scheduleCallable;
        this.periodicDelay = period;
        this.count = count;
        this.timeUnit = timeUnit;
        temporalWindow = new TemporalWindow<ISchedulableEvent<E>>();
        scheduledEvents = new ArrayList<ISchedulableEvent<E>>();
    }

    public SchedulableQueue(IScheduleCallable scheduleCallable, long period, int count, long duration, TimeUnit timeUnit) {
        this.callable = scheduleCallable;
        this.periodicDelay = period;
        this.count = count;
        this.timeUnit = timeUnit;
        this.duration = duration;
        temporalWindow = new TemporalWindow<ISchedulableEvent<E>>();
        scheduledEvents = new ArrayList<ISchedulableEvent<E>>();
    }

    public SchedulableQueue(long period, int count, TimeUnit timeUnit) {
        this.periodicDelay = period;
        this.count = count;
        this.timeUnit = timeUnit;
        temporalWindow = new TemporalWindow<ISchedulableEvent<E>>();
        scheduledEvents = new ArrayList<ISchedulableEvent<E>>();
    }

    public SchedulableQueue(IScheduleCallable scheduleCallable, long period) {
        this.callable = scheduleCallable;
        this.periodicDelay = period;
        temporalWindow = new TemporalWindow<ISchedulableEvent<E>>();
        scheduledEvents = new ArrayList<ISchedulableEvent<E>>();
    }

    public SchedulableQueue(IScheduleCallable scheduleCallable, long period, TimeUnit timeUnit) {
        this.callable = scheduleCallable;
        this.periodicDelay = period;
        this.timeUnit = timeUnit;
        temporalWindow = new TemporalWindow<ISchedulableEvent<E>>();
        scheduledEvents = new ArrayList<ISchedulableEvent<E>>();
    }

    public void schedulePollAtRate() {
        IEPSExecutorManager executorManager = getDispatcherService().getExecutorManager();
        executorManager.executeAtFixedRate(new IWorkerCallable<List<ISchedulableEvent<E>>>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String identifier) {
            }

            public List<ISchedulableEvent<E>> call() throws Exception {
                scheduledEvents = getQueueEvents(new Date().getTime());
                if (callable != null) {
                    callable.onScheduleCall(scheduledEvents);
                }
                return scheduledEvents;
            }
        }, periodicDelay, duration, timeUnit);
    }

    public void scheduleByDurationCount() {
        IEPSExecutorManager executorManager = getDispatcherService().getExecutorManager();
        executorManager.execute(new IWorkerCallable<List<ISchedulableEvent<E>>>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String identifier) {
            }

            public List<ISchedulableEvent<E>> call() throws Exception {
                scheduledEvents = getQueueEvents(new Date().getTime());
                List<ISchedulableEvent<E>> objects = new ArrayList<ISchedulableEvent<E>>();
                if (scheduledEvents.size() < count) {
                    return null;
                } else {
                    int i = 0;
                    for (ISchedulableEvent r : scheduledEvents) {
                        if (i <= count) {
                            objects.add(r);
                            i++;
                        }
                    }
                    if (callable != null) {
                        callable.onScheduleCall(objects);
                    }
                    return objects;
                }
            }
        }, duration, timeUnit);
    }

    public void scheduleByDuration() {
        IEPSExecutorManager executorManager = getDispatcherService().getExecutorManager();
        executorManager.execute(new IWorkerCallable<List<ISchedulableEvent<E>>>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String identifier) {
            }

            public List<ISchedulableEvent<E>> call() throws Exception {
                scheduledEvents = getQueueEvents(new Date().getTime());
                if (callable != null) {
                    callable.onScheduleCall(scheduledEvents);
                }
                return scheduledEvents;
            }
        }, duration, timeUnit);
    }

    public void scheduleWithFixedDelayByCount() {
        IEPSExecutorManager executorManager = getDispatcherService().getExecutorManager();
        executorManager.executeWithFixedDelay(new IWorkerCallable<List<ISchedulableEvent<E>>>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String identifier) {
            }

            public List<ISchedulableEvent<E>> call() throws Exception {
                scheduledEvents = getQueueEvents(new Date().getTime());
                List<ISchedulableEvent<E>> objects = new ArrayList<ISchedulableEvent<E>>();
                if (scheduledEvents.size() < count) {
                    return null;
                } else {
                    int i = 0;
                    for (ISchedulableEvent r : scheduledEvents) {
                        if (i <= count) {
                            objects.add(r);
                            i++;
                        }
                    }
                    if (callable != null) {
                        callable.onScheduleCall(objects);
                    }
                    return objects;
                }
            }
        }, duration, periodicDelay, timeUnit);
    }

    public void scheduleWithFixedDelay() {
        IEPSExecutorManager executorManager = getDispatcherService().getExecutorManager();
        executorManager.executeWithFixedDelay(new IWorkerCallable<List<ISchedulableEvent<E>>>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String identifier) {
            }

            public List<ISchedulableEvent<E>> call() throws Exception {
                scheduledEvents = getQueueEvents(new Date().getTime());
                if (callable != null) {
                    callable.onScheduleCall(scheduledEvents);
                }
                return scheduledEvents;
            }
        }, duration, periodicDelay, timeUnit);
    }

    public void schedulePollAtRateByCount() {
        IEPSExecutorManager executorManager = getDispatcherService().getExecutorManager();
        executorManager.executeAtFixedRate(new IWorkerCallable<List<ISchedulableEvent<E>>>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String identifier) {
            }

            public List<ISchedulableEvent<E>> call() throws Exception {
                scheduledEvents = getQueueEvents(new Date().getTime());

                if (scheduledEvents.size() < count) {
                    return null;
                } else {
                    List<ISchedulableEvent<E>> objects = new ArrayList<ISchedulableEvent<E>>();
                    int i = 0;
                    for (ISchedulableEvent r : scheduledEvents) {
                        if (i <= count) {
                            objects.add(r);
                            i++;
                        }
                    }
                    if (callable != null) {
                        callable.onScheduleCall(objects);
                    }
                    return objects;
                }
            }
        }, duration, periodicDelay, timeUnit);
    }

    public void renewScheduledEvent(long timestamp) {
        this.temporalWindow.adjustWindow(timestamp);
    }

    public List<ISchedulableEvent<E>> getQueueEvents(long ID) {
        scheduledEvents = new ArrayList<ISchedulableEvent<E>>();
        ArrayDeque<ISchedulableEvent<E>> events = temporalWindow.getWindowEvents(ID);
        if (events != null) {
            for (ISchedulableEvent<E> e : events) {
                scheduledEvents.add(e);
            }
        }
        return scheduledEvents;
    }

    public List<ISchedulableEvent<E>> poll(int n) {
        scheduledEvents = new ArrayList<ISchedulableEvent<E>>();
        int i = 0;
        for (ISchedulableEvent<E> r : getQueueEvents(new Date().getTime())) {
            if (i <= n) {
                scheduledEvents.add(r);
                i++;
            } else {
                break;
            }
        }
        return scheduledEvents;
    }

    public void setDispatcherService(IDispatcherService dispatcherService) {
        this.dispatcherService = new WeakReference<IDispatcherService>(dispatcherService);
    }

    public IDispatcherService getDispatcherService() {
        return this.dispatcherService.get();
    }

    public void addToQueue(E event) {
        Long t = new Date().getTime();
        this.temporalWindow.putOrUpdate(t, new SchedulableEvent<E>(IDUtil.getUniqueID(t.toString()), event, t));
    }

    public void setScheduleCallable(IScheduleCallable callable) {
        this.callable = callable;
    }

    public IScheduleCallable getScheduleCallable() {
        return this.callable;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setPeriod(long period) {
        this.periodicDelay = period;
    }

    public long getPeriod() {
        return periodicDelay;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
