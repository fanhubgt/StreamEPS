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
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.dispatch.IDispatcherService;

/**
 * It queue events received from the receiver and will dispatch the accumulated events after
 * the sequence size/threshold is met.
 * 
 * @author Frank Appiah
 * @version 0.3.3
 */
public class WorkerEventQueue<T> implements IWorkerEventQueue<T> {

    private int sequenceSize = 1, tempSize=1;
    private List<T> events = new LinkedList<T>();
    private SoftReference<SortedAccumulator> accumulatorRef;
    private T lastEvent;
    private WeakReference<IDispatcherService> dispatcherServiceRef;
    private WeakReference<IEPSReceiver> receiverRef;
    private String lastEventID;

    public WorkerEventQueue() {
    }

    public WorkerEventQueue(IEPSReceiver receiverRef, int squenceSize) {
        this.receiverRef = new WeakReference<IEPSReceiver>(receiverRef);
        this.sequenceSize = squenceSize;
        this.tempSize = squenceSize;
        accumulatorRef = new SoftReference<SortedAccumulator>(new SortedAccumulator());
    }

    public WorkerEventQueue(int tempSize, IDispatcherService dispatcherService) {
        this.sequenceSize = tempSize;
        this.dispatcherServiceRef = new WeakReference<IDispatcherService>(dispatcherService);
    }

    public WorkerEventQueue(int squenceSize) {
        this.sequenceSize = squenceSize;
        this.tempSize = squenceSize;
        accumulatorRef = new SoftReference<SortedAccumulator>(new SortedAccumulator());
    }

    public void setQueueSize(int size) {
        this.sequenceSize = size;
        this.tempSize = size;
    }

    public void addToQueue(T event, String ID) {
        events = this.accumulatorRef.get().processAt(event.getClass().getName(), event);
        tempSize -= 1;
        if (tempSize < 0) {
            //TODO: implements dispatcher for the decider
            tempSize = sequenceSize;
            lastEventID = event.getClass().getName();
            this.accumulatorRef.get().getMap().remove(lastEventID);
            IEPSReceiver receiver = receiverRef.get();
            receiver.buildContextPartition(receiver.getReceiverContext(), events);
            receiver.pushContextPartition(receiver.getContextPartitions());
        }
        lastEvent = event;
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
        IEPSReceiver receiver = receiverRef.get();
        receiver.buildContextPartition(receiver.getReceiverContext(), accummulatedEvents);
        receiver.pushContextPartition(receiver.getContextPartitions());
    }

    public List<T> getQueueEvents(String ID) {
        //events = accumulator.highest(sequenceSize);
        return this.accumulatorRef.get().getAccumulatedByKey(ID);
    }

    public void setReceiverRef(IEPSReceiver receiver) {
        this.receiverRef = new WeakReference<IEPSReceiver>(receiver);
    }

    public WeakReference<IEPSReceiver> getReceiverRef() {
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

    public void setDispatcherService(IDispatcherService dispatcherService) {
        this.dispatcherServiceRef = new WeakReference<IDispatcherService>(dispatcherService);
    }

    public IDispatcherService getDispatcherService() {
        return this.dispatcherServiceRef.get();
    }

    public ISortedAccumulator getAccumulator() {
        return this.accumulatorRef.get();
    }
}
