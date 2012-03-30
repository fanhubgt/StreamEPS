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

import org.streameps.core.IEventProducer;
import org.streameps.engine.ISchedulableQueue;
import org.streameps.io.netty.IQueueContext;

/**
 * The client proxy for sending events to the remote server.
 * 
 * @author  Frank Appiah
 */
public interface IClientProxy<T> {

    /**
     * It sets the client configuration for the client proxy.
     * @param clientConfigurator
     */
    public void setClientConfigurator(IClientConfigurator clientConfigurator);

    /**
     * It returns the client configuration for the client proxy.
     * @return
     */
    public IClientConfigurator getClientConfigurator();

    /**
     * It configures the event producer for the client proxy.
     */
    public void configure();

    /**
     * It returns the event producer of the client proxy.
     * @return The event producer of the client proxy.
     */
    public IEventProducer<T> getEventProducer();

    /**
     * It sets the event producer for the client proxy.
     * @param eventProducer The event producer of the client proxy.
     */
    public void setEventProducer(IEventProducer<T> eventProducer);

    /**
     * It sends the event received either to a queue or asynchronously to the server
     * for further processing. If it is not queue, it resolves to the main send
     * method.
     * @param event The event instance to send to server.
     */
    public void sendEvent(T event, boolean queue);

    /**
     * It sends the event received to the server for further processing.
     * @param event The event instance to send to server.
     */
    public void sendEvent(T event);

    /**
     * It sets the queue context for the client proxy.
     * @param queueContext The queue context for the client proxy.
     */
    public void setQueueContext(IQueueContext queueContext);

    /**
     * It returns the queue context for the client proxy.
     * @return The queue context for the client proxy.
     */
    public IQueueContext getQueueContext();

    /**
     * It sets the queue of the scheduled client proxy.
     * 
     * @param callable The instance of the callback.
     */
    public void setQueue(ISchedulableQueue queue);

    /**
     * It returns the queue of the scheduled client proxy.
     * @return The instance of the callback.
     */
    public ISchedulableQueue getQueue();
}
