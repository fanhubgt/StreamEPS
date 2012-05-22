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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextPartition;
import org.streameps.dispatch.Dispatchable;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.epn.channel.IEventChannelManager;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.store.file.IFileEPStore;

/**
 * The main processing pipe of the StreamEPS.
 * 
 * @author Frank Appiah
 */
public abstract class AbstractEPSReceiver<C extends IContextPartition, E>
        implements IEPSReceiver<C, E> {

    private WeakReference<IClock> clockRef;
    private WeakReference<IEPSDecider> deciderRef;
    private WeakReference<IEventChannelManager> channelManager;
    private WeakReference<IEPSEngine> epsEngineRef;
    private IContextDetail contextDetail;
    private IWorkerEventQueue eventQueue;
    private WeakReference<IDispatcherService> dispatcherServiceRef;
    private Dispatchable dispatchable;
    private int sequenceCount = 1;
    private List<C> contextPartitions;
    private AtomicLong atomic = new AtomicLong();
    private WeakReference<IHistoryStore> historyStoreRef;
    private IReceiverContext receiverContext;
    private ILogger logger=LoggerUtil.getLogger(this.getClass());

    public AbstractEPSReceiver() {
        this.eventQueue = new WorkerEventQueue(this, sequenceCount);
        this.clockRef = new WeakReference<IClock>(new SystemClock());
        this.contextPartitions = new ArrayList<C>();
    }

    public AbstractEPSReceiver(IClock clock, IEventChannelManager channel) {
        this.clockRef = new WeakReference<IClock>(clock);
        this.eventQueue = new WorkerEventQueue(sequenceCount);
        this.channelManager = new WeakReference<IEventChannelManager>(channel);
        this.contextPartitions = new ArrayList<C>();
    }

    public void onReceive(E event) {
        eventQueue.addToQueue(event, event.getClass().getName());
        historyStoreRef.get().addToStore(IFileEPStore.PARTICIPANT_GROUP, event);
        logger.info("An event has being received from event source....");
    }

    public void pushContextPartition(List<C> partitions) {
        this.getDecider().onContextPartitionReceive(partitions);
        setContextPartitions(partitions);
        logger.info("Pushing the context partition to the partition receiver....");
    }

    public void setDecider(IEPSDecider decider) {
        this.deciderRef = new WeakReference<IEPSDecider>(decider);
    }

    public IEPSDecider getDecider() {
        return this.deciderRef.get();
    }

    public void setClock(IClock clock) {
        this.clockRef = new WeakReference<IClock>(clock);
    }

    public IClock getClock() {
        return this.clockRef.get();
    }

    public void setChannelManager(IEventChannelManager channel) {
        this.channelManager = new WeakReference<IEventChannelManager>(channel);
    }

    public IEventChannelManager getChannelManager() {
        return this.channelManager.get();
    }

    public void setEPSEngine(IEPSEngine engine) {
        this.epsEngineRef = new WeakReference<IEPSEngine>(engine);
    }

    public void setContextPartitions(List<C> contextPartitions) {
        this.contextPartitions = contextPartitions;
    }

    public IEPSEngine getEPSEngine() {
        return this.epsEngineRef.get();
    }

    public void setHistoryStore(IHistoryStore historyStore) {
        this.historyStoreRef = new WeakReference<IHistoryStore>(historyStore);
    }

    public IHistoryStore getHistoryStore() {
        return this.historyStoreRef.get();
    }

    public List<C> getContextPartitions() {
        if (contextPartitions.size() < 0) {
            buildContextPartition(receiverContext);
        }
        return contextPartitions;
    }

    public void setContextDetail(IContextDetail contextDetail) {
        this.contextDetail = contextDetail;
    }

    public IContextDetail getContextDetail() {
        return this.contextDetail;
    }

    public void setReceiverContext(IReceiverContext receiverContext) {
        this.receiverContext = receiverContext;
        this.contextDetail = receiverContext.getContextDetail();
    }

    public IReceiverContext getReceiverContext() {
        return this.receiverContext;
    }

    public IWorkerEventQueue getEventQueue() {
        return eventQueue;
    }

    public void setEventQueue(IWorkerEventQueue eventQueue) {
        this.eventQueue=eventQueue;
    }

    public ILogger getLogger() {
        return logger;
    }

}
