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
package org.streameps.io.netty.client;

import java.util.List;
import org.streameps.core.IEventProducer;
import org.streameps.core.ISchedulableEvent;
import org.streameps.core.IStreamEvent;
import org.streameps.engine.ISchedulableQueue;
import org.streameps.engine.IScheduleCallable;
import org.streameps.engine.SchedulableQueue;
import org.streameps.io.netty.IQueueContext;

/**
 *
 * @author Frank Appiah
 */
public class ClientProxyQ implements IClientProxy<IStreamEvent>, IScheduleCallable<IStreamEvent> {

    private IClientConfigurator clientConfigurator;
    private IEventProducer<IStreamEvent> eventProducer;
    private ISchedulableQueue schedulableQueue;
    private IQueueContext queueContext;

    public ClientProxyQ() {
        clientConfigurator = new ClientConfigurator();
        schedulableQueue = new SchedulableQueue(this);
    }

    public ClientProxyQ(IClientConfigurator clientConfigurator, IEventProducer<IStreamEvent> eventProducer, ISchedulableQueue schedulableQueue, IQueueContext queueContext) {
        this.clientConfigurator = clientConfigurator;
        this.eventProducer = eventProducer;
        this.schedulableQueue = schedulableQueue;
        this.queueContext = queueContext;
    }

    public ClientProxyQ(IClientConfigurator clientConfigurator, IEventProducer<IStreamEvent> eventProducer) {
        this.clientConfigurator = clientConfigurator;
        this.eventProducer = eventProducer;
        this.schedulableQueue = new SchedulableQueue(this);
    }

    public ClientProxyQ(IClientConfigurator clientConfigurator) {
        this.clientConfigurator = clientConfigurator;
        this.schedulableQueue = new SchedulableQueue(this);
    }

    public void setClientConfigurator(IClientConfigurator clientConfigurator) {
        this.clientConfigurator = clientConfigurator;
    }

    public IClientConfigurator getClientConfigurator() {
        return this.clientConfigurator;
    }

    public void init() {
        schedulableQueue.setScheduleCallable(this);
        if (queueContext != null) {
            schedulableQueue.setCount(queueContext.getQueueSize());
            schedulableQueue.setPeriod(queueContext.getPeriod());
            schedulableQueue.setTimeUnit(queueContext.getTimeUnit());
        }
    }

    public void configure() {
        init();
        clientConfigurator.configure();
    }

    public IEventProducer getEventProducer() {
        return this.eventProducer;
    }

    public void setEventProducer(IEventProducer eventProducer) {
        this.eventProducer = eventProducer;
    }

    public void sendEvent(IStreamEvent event, boolean queue) {
        if (!queue) {
            if (getEventProducer() != null) {
                getEventProducer().sendEvent(event);
            }
        } else {
            schedulableQueue.addToQueue(event);
        }
    }

    public void sendEvent(IStreamEvent event) {
        if (getEventProducer() != null) {
            getEventProducer().sendEvent(event);
        }
    }

    public void setQueueContext(IQueueContext queueContext) {
        this.queueContext = queueContext;
    }

    public IQueueContext getQueueContext() {
        return this.queueContext;
    }

    public void onScheduleCall(List<ISchedulableEvent<IStreamEvent>> schedulableEvents) {
        for (ISchedulableEvent<IStreamEvent> event : schedulableEvents) {
            IStreamEvent stream = event.getEvent();
            if (getEventProducer() != null) {
                getEventProducer().sendEvent(stream);
            }
        }
    }

    public ISchedulableQueue getQueue() {
        return this.schedulableQueue;
    }

    public void setQueue(ISchedulableQueue queue) {
        this.schedulableQueue = queue;
        this.schedulableQueue.setScheduleCallable(this);
    }
}
