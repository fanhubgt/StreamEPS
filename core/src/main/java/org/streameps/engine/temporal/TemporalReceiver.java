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

import java.util.List;
import org.streameps.context.IContextDetail;
import org.streameps.context.IContextPartition;
import org.streameps.context.temporal.TemporalType;
import org.streameps.engine.AbstractEPSReceiver;
import org.streameps.engine.IClock;
import org.streameps.engine.IEPSDecider;
import org.streameps.engine.IEPSEngine;
import org.streameps.engine.IEPSReceiver;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.IReceiverContext;
import org.streameps.engine.IReceiverPair;
import org.streameps.engine.IRouterContext;
import org.streameps.engine.IWorkerEventQueue;
import org.streameps.epn.channel.IEventChannelManager;

/**
 *
 * @author Frank Appiah
 */
public class TemporalReceiver<T extends IContextDetail, E>
        extends AbstractEPSReceiver<IContextPartition<T>, E>
        implements ITemporalReceiver<T, E> {

    private AbstractEPSReceiver<IContextPartition<T>, E> receiver;
    private TemporalType temporalType;

    public TemporalReceiver() {
        super();
    }

    public TemporalReceiver(AbstractEPSReceiver<IContextPartition<T>, E> receiver) {
        super();
        this.receiver = receiver;
    }

    public TemporalReceiver(AbstractEPSReceiver<IContextPartition<T>, E> receiver, TemporalType temporalType) {
        super();
        this.receiver = receiver;
        this.temporalType = temporalType;
    }

    @Override
    public void onReceive(E event) {
        this.receiver.onReceive(event);
    }

    @Override
    public void pushContextPartition(List<IContextPartition<T>> partitions) {
        this.receiver.pushContextPartition(partitions);
    }

    public void routeEvent(E event, IReceiverPair<? extends IRouterContext, ? extends IReceiverContext> receiverPair) {
        receiver.routeEvent(event, receiverPair);
    }

    public void buildContextPartition(IReceiverContext receiverContext, List<E> events) {
        receiver.buildContextPartition(receiverContext, events);
    }

    public void buildContextPartition(IReceiverContext receiverContext) {
        receiver.buildContextPartition(receiverContext);
    }

    public void setReceiver(AbstractEPSReceiver<IContextPartition<T>, E> receiver) {
        this.receiver = receiver;
        this.receiver.getEPSEngine().setEPSReceiver(this);
    }

    public void setTemporalType(TemporalType temporalType) {
        this.temporalType = temporalType;
    }

    public TemporalType getTemporalType() {
        return temporalType;
    }

    @Override
    public void setChannelManager(IEventChannelManager channel) {
        this.receiver.setChannelManager(channel);
    }

    @Override
    public void setClock(IClock clock) {
        this.receiver.setClock(clock);
    }

    @Override
    public void setContextPartitions(List<IContextPartition<T>> contextPartitions) {
        this.receiver.setContextPartitions(contextPartitions);
    }

    @Override
    public void setDecider(IEPSDecider decider) {
        this.receiver.setDecider(decider);
    }

    @Override
    public void setEPSEngine(IEPSEngine engine) {
        this.receiver.setEPSEngine(engine);
    }

    @Override
    public void setHistoryStore(IHistoryStore historyStore) {
        this.receiver.setHistoryStore(historyStore);
    }

    @Override
    public void setEventQueue(IWorkerEventQueue eventQueue) {
        this.receiver.setEventQueue(eventQueue);
    }

    @Override
    public void setReceiverContext(IReceiverContext receiverContext) {
        this.receiver.setReceiverContext(receiverContext);
    }

    @Override
    public IEventChannelManager getChannelManager() {
        return this.receiver.getChannelManager();
    }

    @Override
    public IClock getClock() {
        return this.receiver.getClock();
    }

    @Override
    public List<IContextPartition<T>> getContextPartitions() {
        return this.receiver.getContextPartitions();
    }

    @Override
    public IEPSDecider getDecider() {
        return this.receiver.getDecider();
    }

    @Override
    public IEPSEngine getEPSEngine() {
        return this.receiver.getEPSEngine();
    }

    @Override
    public IWorkerEventQueue getEventQueue() {
        return this.receiver.getEventQueue();
    }

    @Override
    public IHistoryStore getHistoryStore() {
        return this.receiver.getHistoryStore();
    }

    @Override
    public IReceiverContext getReceiverContext() {
        return this.receiver.getReceiverContext();
    }

    public AbstractEPSReceiver<IContextPartition<T>, E> getReceiver() {
        return this.receiver;
    }

    public void setReceiver(IEPSReceiver<IContextPartition<T>, E> receiver) {
        this.receiver = (AbstractEPSReceiver<IContextPartition<T>, E>) receiver;
    }

    @Override
    public IContextDetail getContextDetail() {
        return this.receiver.getContextDetail();
    }

    @Override
    public void setContextDetail(IContextDetail contextDetail) {
        this.receiver.setContextDetail(contextDetail);
    }
    
}
