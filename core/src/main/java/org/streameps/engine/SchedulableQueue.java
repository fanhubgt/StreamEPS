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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.streameps.aggregation.collection.ITemporalWindow;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.aggregation.collection.TemporalWindow;
import org.streameps.core.ISchedulableEvent;
import org.streameps.core.SchedulableEvent;
import org.streameps.core.util.IDUtil;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.thread.IEPSExecutorManager;
import org.streameps.thread.IResultUnit;
import org.streameps.thread.IWorkerCallable;

/**
 *
 * @author Frank Appiah
 */
public class SchedulableQueue implements ISchedulableQueue {

    private List<ISchedulableEvent> scheduledEvent;
    private SoftReference<SortedAccumulator> accumulatorRef;
    private ISchedulableEvent<?> lastEvent;
    private WeakReference<IDispatcherService> dispatcherService;
    private ITemporalWindow<ISchedulableEvent<?>> temporalWindow;

    public SchedulableQueue() {
        super();
        temporalWindow = new TemporalWindow<ISchedulableEvent<?>>();
    }

    public void pollScheduleAtRate(long period) {
        IEPSExecutorManager executorManager = getDispatcherService().getExecutorManager();
        executorManager.executeAtFixedRate(new IWorkerCallable<List<ISchedulableEvent>>() {

            public String getIdentifier() {
                return IDUtil.getUniqueIDRand();
            }

            public void setIdentifier(String name) {
            }

            public List<ISchedulableEvent> call() throws Exception {
                return getQueueEvents(new Date().getTime());
            }
        }, 0, period, TimeUnit.SECONDS);
        IResultUnit<List<ISchedulableEvent>> resultUnit = (IResultUnit<List<ISchedulableEvent>>)
                executorManager.getFutureResultQueue().getNextResultUnit();
        try {
            scheduledEvent = resultUnit.getScheduledFuture().get();
        } catch (InterruptedException ex) {
            Logger.getLogger(WorkerEventQueue.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(WorkerEventQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void pollScheduleAtRate(final int n, long period) {
        IEPSExecutorManager executorManager = getDispatcherService().getExecutorManager();
        executorManager.executeAtFixedRate(new IWorkerCallable<List<ISchedulableEvent>>() {

            public String getIdentifier() {
                return IDUtil.getUniqueIDRand();
            }

            public void setIdentifier(String name) {
            }

            public List<ISchedulableEvent> call() throws Exception {
                if (getQueueEvents(new Date().getTime()).size() < n) {
                    return getQueueEvents(new Date().getTime());
                } else {
                    List<ISchedulableEvent> objects = new ArrayList<ISchedulableEvent>();
                    int i = 0;
                    for (ISchedulableEvent r : getQueueEvents(new Date().getTime())) {
                        if (i <= n) {
                            objects.add(r);
                            i++;
                        }
                    }
                    return objects;
                }
            }
        }, 0, period, TimeUnit.SECONDS);
        IResultUnit<List<ISchedulableEvent>> resultUnit = (IResultUnit<List<ISchedulableEvent>>)
                executorManager.getFutureResultQueue().getNextResultUnit();
        try {
            scheduledEvent = resultUnit.getScheduledFuture().get();
        } catch (InterruptedException ex) {
            Logger.getLogger(WorkerEventQueue.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) {
            Logger.getLogger(WorkerEventQueue.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void renewScheduledEvent(long timestamp) {
        this.temporalWindow.adjustWindow(timestamp);
    }

    public void addToQueue(ISchedulableEvent event) {
        this.temporalWindow.putOrUpdate(new Date().getTime(), event);
    }

    public List<ISchedulableEvent> getQueueEvents(long ID) {
        scheduledEvent = new ArrayList<ISchedulableEvent>();
        for (ISchedulableEvent e : temporalWindow.getWindowEvents(ID)) {
            scheduledEvent.add(e);
        }
        return scheduledEvent;
    }

    public List<ISchedulableEvent> poll(int n) {
        scheduledEvent = new ArrayList<ISchedulableEvent>();
        int i = 0;
        for (ISchedulableEvent r : getQueueEvents(new Date().getTime())) {
            if (i <= n) {
                scheduledEvent.add(r);
                i++;
            }
        }
        return scheduledEvent;
    }

    public void setDispatcherService(IDispatcherService dispatcherService) {
        this.dispatcherService = new WeakReference<IDispatcherService>(dispatcherService);
    }

    public IDispatcherService getDispatcherService() {
        return this.dispatcherService.get();
    }

    public void addToQueue(Object event) {
        Long t=new Date().getTime();
        this.temporalWindow.putOrUpdate(t, 
                new SchedulableEvent<Object>
                (IDUtil.getUniqueID(t.toString()), event, t));
    }

}
