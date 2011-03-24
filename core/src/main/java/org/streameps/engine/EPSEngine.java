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

import org.streameps.context.IContextPartition;
import org.streameps.core.PreProcessAware;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.epn.channel.IEventChannel;
import org.streameps.processor.pattern.IBasePattern;

/**
 * Implementation of the event processing engine for a general context partition.
 * Supported context partition includes temporal, spatial, segment, state.
 * The engine for the event processing system supports queued events, asynchronous
 * and synchronous dispatch of events via the indicator set by the developer.
 * The EPS engine is pre(post)-process aware.
 * 
 * @see PreProcessAware
 * 
 * @author Frank Appiah
 * @version 0.3.3
 */
public abstract class EPSEngine<C extends IContextPartition, B extends IBasePattern> implements IEPSEngine, PreProcessAware {

    private IClock clock;
    private IEventChannel channel;
    private IEPSDecider<C, B> decider;
    private C contextPartition;
    private IPatternChain<B> basePattern;
    private IWorkerEventQueue eventQueue;
    private int sequenceCount = 1;
    private boolean asynchronous = false;
    private boolean eventQueued = false;
    private IDispatcherService dispatcherService;

    public EPSEngine() {
        eventQueue = new WorkerEventQueue(sequenceCount, dispatcherService);
    }

    public EPSEngine(IClock clock, IEventChannel channel,
            IEPSDecider<C, B> decider,
            C contextPartition) {
        this.clock = clock;
        this.channel = channel;
        this.decider = decider;
        this.contextPartition = contextPartition;
    }

    private void sendOnReceiveAsynch(Object event, boolean asych) {
        Object preEvent = preProcessOnRecieve(event);
        if (isEventQueued()) {
            eventQueue.addToQueue(preProcessOnRecieve(event));
            return;
        }
    }

    public void setClock(IClock clock) {
        this.clock = clock;
    }

    public IClock getClock() {
        return this.clock;
    }

    public void setChannel(IEventChannel channel) {
        this.channel = channel;
    }

    public IEventChannel getChannel() {
        return this.channel;
    }

    public void setDecider(IEPSDecider decider) {
        this.decider = decider;
    }

    public void setContextPartition(C contextPartition) {
        this.contextPartition = contextPartition;
        this.decider.setContextPartition(contextPartition);
    }

    public void setBasePattern(IPatternChain<B> basePattern) {
        this.basePattern = basePattern;
        this.decider.setPatternChain(basePattern);
    }

    public IPatternChain<B> getBasePattern() {
        return basePattern;
    }

    public C getContextPartition() {
        return contextPartition;
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
    }

    public IDispatcherService getDispatcherService() {
        return dispatcherService;
    }

    public IWorkerEventQueue getEventQueue() {
        return eventQueue;
    }

    public abstract IEPSDecider<C, B> getDecider();
}
