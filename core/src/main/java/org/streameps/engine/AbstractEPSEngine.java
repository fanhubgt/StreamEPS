/*
 * ====================================================================
 *  StreamEPS Platform
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
 *  =============================================================================
 */
package org.streameps.engine;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import org.streameps.context.IContextPartition;
import org.streameps.core.DomainManager;
import org.streameps.core.IDomainManager;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.PrePostProcessAware;
import org.streameps.core.util.IDUtil;
import org.streameps.dispatch.DispatcherService;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.store.file.IFileEPStore;
import org.streameps.thread.IEPSExecutorManager;

/**
 * Implementation of the event processing engine for a general context partition.
 * Supported context partition includes temporal, spatial, segment, state.
 * The engine for the event processing system supports queued events, asynchronous
 * and synchronous dispatch of events via the indicator set by the developer.
 * The EPS engine is pre(post)-process aware.
 * 
 * @see PrePostProcessAware
 * 
 * @author Frank Appiah
 * @version 0.3.3
 */
public abstract class AbstractEPSEngine<C extends IContextPartition, E>
        implements IEPSEngine<C, E>, PrePostProcessAware {

    private List<C> contextPartitions;
    private IEPSDecider<C> decider;
    private IPatternChain<IBasePattern> basePattern;
    private int sequenceCount = 1;
    private boolean asynchronous = true;
    private boolean eventQueued = false;
    private IDispatcherService dispatcherService;
    private PrePostProcessAware enginePrePostAware = null;
    private IDomainManager domainManager;
    private ConcurrentMap<String, String> mapIDClass;
    private IEPSReceiver<C, E> epsReceiver;
    private IWorkerEventQueue<E> eventQueue;
    private ISchedulableQueue schedulableQueue;
    private IDeciderContext<IMatchedEventSet> deciderContext;
    private int dispatcherSize = 1;
    private long initialDelay = 0, periodicDelay = 100;
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
    private IEPSExecutorManager executorManager;
    private boolean saveOnReceive = false;
    private boolean saveOnDecide = false;
    private IHistoryStore<E> auditStore;

    public AbstractEPSEngine() {
        eventQueue = new WorkerEventQueue(sequenceCount);
        domainManager = new DomainManager();
        mapIDClass = new ConcurrentHashMap<String, String>();
        dispatcherService = new DispatcherService();
        auditStore = new AuditEventStore<E>();
    }

    public AbstractEPSEngine(List<C> contextPartitions) {
        this.contextPartitions = contextPartitions;
        domainManager = new DomainManager();
        eventQueue = new WorkerEventQueue(sequenceCount);
        mapIDClass = new ConcurrentHashMap<String, String>();
        dispatcherService = new DispatcherService();
        auditStore = new AuditEventStore<E>();
    }

    public void sendEvent(E event, boolean asynch) {
        String key = event.getClass().getName();
        if (asynch || isAsynchronous()) {
            getEventQueue().addToQueue(event, key);
            getEventQueue().buildContextPartition(epsReceiver.getReceiverContext());
        } else {
            epsReceiver.onReceive(event);
        }
        if (saveOnReceive) {
            auditStore.addToStore(IFileEPStore.PARTICIPANT_GROUP, event);
        }
        mapIDClass.put(IDUtil.getUniqueID(new Date().toString()), key);
    }

    public void onDeciderContextReceive(IDeciderContext deciderContext) {
        this.deciderContext = deciderContext;
    }

    public void setContextPartitions(List<C> contextPartition) {
        this.contextPartitions = contextPartition;
        this.decider.setContextPartition(contextPartition);
    }

    public void setBasePattern(IPatternChain<IBasePattern> basePattern) {
        this.basePattern = basePattern;
        this.decider.setPatternChain(basePattern);
    }

    public void setEPSReceiver(IEPSReceiver<C, E> sReceiver) {
        this.epsReceiver = sReceiver;
        this.epsReceiver.setEPSEngine(this);
        this.epsReceiver.setEventQueue(eventQueue);
        this.epsReceiver.setDecider(decider);
        ((WorkerEventQueue) getEventQueue()).setReceiverRef(sReceiver);
        ((WorkerEventQueue) getEventQueue()).setExecutorManager(domainManager.getExecutorManager());
    }

    public IEPSReceiver<C, E> getEPSReceiver() {
        return this.epsReceiver;
    }

    public int getDispatcherSize() {
        return this.dispatcherSize;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
        getDispatcherService().setIntialDelay(initialDelay);
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public long getPeriodicDelay() {
        return periodicDelay;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        getDispatcherService().setTimeUnit(timeUnit);
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setPeriodicDelay(long periodicDelay) {
        this.periodicDelay = periodicDelay;
        getDispatcherService().setPeriod(periodicDelay);
    }

    public void setDispatcherSize(int size) {
        this.dispatcherSize = size;
        getDispatcherService().setDispatchableSize(dispatcherSize);
    }

    public void setDecider(IEPSDecider decider) {
        this.decider = decider;
        this.decider.setSaveOnDecide(saveOnDecide);
        getEPSReceiver().setDecider(this.decider);
    }

    public IEPSDecider<C> getDecider() {
        return this.decider;
    }

    public IPatternChain<IBasePattern> getBasePattern() {
        return basePattern;
    }

    public List<C> getContextPartitions() {
        return contextPartitions;
    }

    public void setSequenceCount(int sequenceCount) {
        this.sequenceCount = sequenceCount;
        eventQueue.setQueueSize(sequenceCount);
    }

    public int getSequenceCount() {
        return sequenceCount;
    }

    public void setAsynchronous(boolean asynchronous) {
        this.asynchronous = asynchronous;
    }

    public boolean isAsynchronous() {
        return asynchronous;
    }

    public boolean isEventQueued() {
        return eventQueued;
    }

    public void setEventQueued(boolean eventQueued) {
        this.eventQueued = eventQueued;
    }

    public void setDispatcherService(IDispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
        this.dispatcherService.setEngine(this);
        this.dispatcherService.setDispatchableSize(dispatcherSize);
        this.dispatcherService.setIntialDelay(initialDelay);
        this.dispatcherService.setPeriod(periodicDelay);
        this.dispatcherService.setTimeUnit(timeUnit);

        executorManager = domainManager.getExecutorManager();
        setExecutorManager(executorManager);
        this.dispatcherService.setExecutionManager(executorManager);

        getEventQueue().setDispatcherService(dispatcherService);
    }

    public IDispatcherService getDispatcherService() {
        return dispatcherService;
    }

    public IWorkerEventQueue getEventQueue() {
        return eventQueue;
    }

    public PrePostProcessAware getEnginePrePostAware() {
        if (enginePrePostAware == null) {
            enginePrePostAware = new DefaultEnginePrePostAware();
        }
        return enginePrePostAware;
    }

    public void setEnginePrePostAware(PrePostProcessAware enginePrePostAware) {
        this.enginePrePostAware = enginePrePostAware;
    }

    public void setDomainManager(IDomainManager domainManager) {
        this.domainManager = domainManager;
    }

    public IDomainManager getDomainManager() {
        return domainManager;
    }

    public Map<String, String> getMapIDClass() {
        return mapIDClass;
    }

    public void setMapIDClass(Map<String, String> mapIDClass) {
        this.mapIDClass = (ConcurrentMap<String, String>) mapIDClass;
    }

    public void setExecutorManager(IEPSExecutorManager executorManager) {
        this.executorManager = executorManager;
    }

    public IEPSExecutorManager getExecutorManager() {
        return executorManager;
    }

    public void setSaveOnReceive(boolean saveOnReceive) {
        this.saveOnReceive = saveOnReceive;
    }

    public boolean isSaveOnReceive() {
        return saveOnReceive;
    }

    public void setSaveOnDecide(boolean saveOnDecide) {
        this.saveOnDecide = saveOnDecide;
        getDecider().setSaveOnDecide(saveOnDecide);
    }

    public boolean isSaveOnDecide() {
        return saveOnDecide;
    }

    public IHistoryStore<E> getAuditStore() {
        return auditStore;
    }

    public void setAuditStore(IHistoryStore<E> auditStore) {
        this.auditStore = auditStore;
        this.auditStore.configureStore();
    }

    public void persistAuditEvents() {
        if (isSaveOnReceive()) {
            getAuditStore().save();
        }
    }
}
