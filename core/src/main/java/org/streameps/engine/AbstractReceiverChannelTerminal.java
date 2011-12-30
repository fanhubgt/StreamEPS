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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.streameps.core.IEventType;
import org.streameps.core.ISchedulableEvent;
import org.streameps.core.util.IDUtil;
import org.streameps.dispatch.DispatcherService;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.filter.FilterManager;
import org.streameps.filter.IFilterManager;
import org.streameps.processor.EventAggregatorPE;
import org.streameps.processor.IEventAggregator;
import org.streameps.processor.IPatternManager;
import org.streameps.processor.PatternManager;
import org.streameps.thread.IEPSExecutorManager;

/**
 *
 * @author Frank Appiah
 */
public abstract class AbstractReceiverChannelTerminal<T> implements IReceiverChannelTerminal<T> {

    private IFilterManager filterManager;
    private IPatternManager<T> patternManager;
    private IEventAggregator eventAggregator;
    private int squenceSize = 10;
    private List<IEventType<T>> eventTypes;
    private IEPSForwarder<T> forwarder;
    private IEPSProducer producer;
    private DispatcherService dispatcherService;
    private ISchedulableQueue<T> schedulableQueue;
    private boolean started = false, stopped = false;
    private Set<T> newEvents = new HashSet<T>();
    private IAggregateContext aggregateContext;
    private IFilterContext filterContext;
    private IEPSExecutorManager executorManager;

    public AbstractReceiverChannelTerminal() {
        filterManager = new FilterManager();
        patternManager = new PatternManager<T>();
        eventAggregator = new EventAggregatorPE();
        dispatcherService = new DispatcherService();
        eventTypes = new ArrayList<IEventType<T>>();
        schedulableQueue = new SchedulableQueue<T>(this);
        producer = new EPSProducer();
        forwarder=new EPSForwarder<T>();
    }

    public AbstractReceiverChannelTerminal(IFilterManager filterManager, IPatternManager<T> patternManager,
            IEventAggregator eventAggregator) {
        this.filterManager = filterManager;
        this.patternManager = patternManager;
        this.eventAggregator = eventAggregator;
    }

    public void receiveEvent(T event, boolean asynch) {
        if (validateTypes(event)) {
            schedulableQueue.addToQueue(event);
        }
    }

    public void startTerminal() {
        if (!started) {
            IDispatcherService service = getDispatcherService();
            schedulableQueue.setDispatcherService(service);
            schedulableQueue.schedulePollAtRate();
            schedulableQueue.schedulePollAtRateByCount();
            started = true;
        }
    }

    public void stopTerminal() {
        if (!stopped) {
            schedulableQueue.getDispatcherService().getExecutorManager().shutdown();
            stopped = true;
        }
    }

    public boolean isStop() {
        return stopped;
    }

    public boolean isStarted() {
        return started;
    }

    private boolean validateTypes(T event) {
        return true;
    }

    public String getIdentifier() {
        return IDUtil.getUniqueID(new Date().toString());
    }

    public void setEventTypes(List<IEventType<T>> eventTypes) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<IEventType<T>> getEventTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setEventAggregator(IEventAggregator eventAggregator) {
        this.eventAggregator = eventAggregator;
    }

    public void setFilterManager(IFilterManager filterManager) {
        this.filterManager = filterManager;
        this.producer.setFilterManager(filterManager);
    }

    public void setPatternManager(IPatternManager<T> patternManager) {
        this.patternManager = patternManager;
    }

    public IEventAggregator getEventAggregator() {
        return eventAggregator;
    }

    public IEPSProducer getProducer() {
        return producer;
    }

    public ISchedulableQueue<T> getSchedulableQueue() {
        return schedulableQueue;
    }

    public DispatcherService getDispatcherService() {
        return dispatcherService;
    }

    public void setSchedulableQueue(ISchedulableQueue<T> schedulableQueue) {
        this.schedulableQueue = schedulableQueue;
        this.schedulableQueue.setScheduleCallable(this);
    }

    public void setProducer(IEPSProducer producer) {
        this.producer = producer;
        this.producer.setAggregateContext(aggregateContext);
        this.producer.setFilterContext(filterContext);
        this.producer.setForwarder(forwarder);
        this.producer.setFilterManager(filterManager);
    }

    public void setDispatcherService(DispatcherService dispatcherService) {
        this.dispatcherService = dispatcherService;
        this.schedulableQueue.setDispatcherService(dispatcherService);
        this.dispatcherService.setExecutionManager(executorManager);
    }

    public IFilterManager getFilterManager() {
        return filterManager;
    }

    public IPatternManager<T> getPatternManager() {
        return patternManager;
    }

    public int getSquenceSize() {
        return squenceSize;
    }

    public void setSquenceSize(int squenceSize) {
        this.squenceSize = squenceSize;
    }

    public void setForwarder(IEPSForwarder<T> forwarder) {
        this.forwarder = forwarder;
        this.producer.setForwarder(forwarder);
    }

    public IEPSForwarder<T> getForwarder() {
        return forwarder;
    }

    public void setAggregateContext(IAggregateContext aggregateContext) {
        this.aggregateContext = aggregateContext;
        this.producer.setAggregateContext(aggregateContext);
    }

    public IAggregateContext getAggregateContext() {
        return aggregateContext;
    }

    public IFilterContext getFilterContext() {
        return filterContext;
    }

    public void setFilterContext(IFilterContext filterContext) {
        this.filterContext = filterContext;
        this.producer.setFilterContext(filterContext);
    }

    public void setExecutorManager(IEPSExecutorManager executorManager) {
        this.executorManager = executorManager;
        this.dispatcherService.setExecutionManager(executorManager);
        this.schedulableQueue.setDispatcherService(dispatcherService);
    }

    public IEPSExecutorManager getExecutorManager() {
        return executorManager;
    }

    public abstract void onScheduleCall(List<ISchedulableEvent<T>> schedulableEvents);
}
