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
package org.streameps.engine;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.dispatch.Dispatchable;
import org.streameps.dispatch.DispatcherService;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.thread.IEPSExecutorManager;

/**
 * It queue events received from the receiver and will dispatch the accumulated events after
 * the sequence size/threshold is met.
 * 
 * @author Frank Appiah
 * @version 0.3.3
 */
public class WorkerEventQueue<T> implements IWorkerEventQueue<T> {

    private int sequenceSize = 1, tempSize = 1;
    private List<T> events = new LinkedList<T>();
    private List<T> tempEvents = new LinkedList<T>();
    private SoftReference<SortedAccumulator> accumulatorRef;
    private T lastEvent;
    private IDispatcherService dispatcherServiceRef;
    private IEPSReceiver receiverRef;
    private String lastEventID;
    private IEPSExecutorManager executorManagerRef;
    private AtomicInteger ai;
    private String identifier;
    private boolean dispatched = false;
    private int lastCount = 0;
    private CountDownLatch countDownLatch;
    private ILogger logger=LoggerUtil.getLogger(WorkerEventQueue.class);

    public WorkerEventQueue() {
        this.accumulatorRef = new SoftReference<SortedAccumulator>(new SortedAccumulator());
        this.ai = new AtomicInteger(sequenceSize);
        dispatcherServiceRef = new DispatcherService();
        countDownLatch = new CountDownLatch(sequenceSize);
    }

    public WorkerEventQueue(IEPSReceiver receiverRef, int squenceSize) {
        this.sequenceSize = squenceSize;
        this.tempSize = squenceSize;
        this.accumulatorRef = new SoftReference<SortedAccumulator>(new SortedAccumulator());
        this.ai = new AtomicInteger(sequenceSize);
        dispatcherServiceRef = new DispatcherService();
        countDownLatch = new CountDownLatch(sequenceSize);
    }

    public WorkerEventQueue(int tempSize, IDispatcherService dispatcherService) {
        this.sequenceSize = tempSize;
        this.accumulatorRef = new SoftReference<SortedAccumulator>(new SortedAccumulator());
        this.ai = new AtomicInteger(sequenceSize);
        dispatcherServiceRef = new DispatcherService();
        countDownLatch = new CountDownLatch(sequenceSize);
    }

    public WorkerEventQueue(int squenceSize) {
        this.sequenceSize = squenceSize;
        this.tempSize = squenceSize;
        this.accumulatorRef = new SoftReference<SortedAccumulator>(new SortedAccumulator());
        this.ai = new AtomicInteger(sequenceSize);
        dispatcherServiceRef = new DispatcherService();
        countDownLatch = new CountDownLatch(sequenceSize);
    }

    public void setQueueSize(int size) {
        this.sequenceSize = size;
        this.tempSize = size;
        countDownLatch = new CountDownLatch(sequenceSize);
    }

    public void addToQueue(T event, String ID) {
        events = this.accumulatorRef.get().processAt(event.getClass().getName(), event);
        tempEvents = events;
        countDownLatch.countDown();
        if (countDownLatch.getCount() < 0) {
            countDownLatch = new CountDownLatch(sequenceSize);
            lastEventID = event.getClass().getName();
            lastCount = tempEvents.size();
            addDispatchable();
            dispatched = true;
            logger.info("A new dispatch unit is added to the dispatcher service....");
        }
        lastEvent = event;
    }

    public void addDispatchable() {
        if (dispatcherServiceRef != null) {
            getDispatcherService().registerDispatcher(new Dispatchable() {

                public void dispatch() {
                    accumulatorRef.get().getMap().remove(lastEventID);
                    IEPSReceiver receiver = receiverRef;
                    receiver.buildContextPartition(receiver.getReceiverContext(), tempEvents);
                    receiver.pushContextPartition(receiver.getContextPartitions());
                }
            });
            //((AbstractEPSEngine) receiverRef.getEPSEngine()).persistAuditEvents();
        }
    }

    public String getLastEventID() {
        return lastEventID;
    }

    public void buildContextPartition(IReceiverContext receiverContext) {
        ISortedAccumulator<T> accumulator = getAccumulator();
        List<T> accummulatedEvents = null;
        if (accumulator.getSizeCount() > 0) {
            accummulatedEvents = new ArrayList<T>();
            for (Object key : accumulator.getMap().keySet()) {
                for (T event : accumulator.getAccumulatedByKey(key)) {
                    accummulatedEvents.add(event);
                }
            }
        } else {
            accummulatedEvents = events;
        }
        IEPSReceiver receiver = receiverRef;
        receiver.buildContextPartition(receiver.getReceiverContext(), accummulatedEvents);
        receiver.pushContextPartition(receiver.getContextPartitions());
    }

    public void setExecutorManager(IEPSExecutorManager executorManager) {
        this.executorManagerRef = executorManager;
    }

    public IEPSExecutorManager getExecutorManager() {
        return executorManagerRef;
    }

    public List<T> getQueueEvents(String ID) {
        return this.accumulatorRef.get().getAccumulatedByKey(ID);
    }

    public void setReceiverRef(IEPSReceiver receiver) {
        this.receiverRef = receiver;
    }

    public IEPSReceiver getReceiverRef() {
        return receiverRef;
    }

    public T getLastEvent() {
        return lastEvent;
    }

    public List<T> poll(int n, String ID) {
        if (getQueueEvents(ID).size() < n) {
            return getQueueEvents(ID);
        } else {
            List<T> objects = new ArrayList<T>();
            int i = 0;
            for (T r : getQueueEvents(ID)) {
                if (i <= n) {
                    objects.add(r);
                    i++;
                }
            }
            return objects;
        }
    }

    public void removeList(List events) {
        Object key = events.get(0);
        for (Object value : events) {
            getAccumulator().getAccumulatedByKey(key.getClass().getName()).remove(value);
        }
    }

    public void setDispatcherService(IDispatcherService dispatcherService) {
        this.dispatcherServiceRef = dispatcherService;
    }

    public IDispatcherService getDispatcherService() {
        return this.dispatcherServiceRef;
    }

    public ISortedAccumulator getAccumulator() {
        return this.accumulatorRef.get();
    }

    public boolean isDispatched() {
        return dispatched;
    }
}
