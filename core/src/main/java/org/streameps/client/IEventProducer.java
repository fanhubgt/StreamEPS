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
package org.streameps.client;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for the event producer in the event processing network.
 * 
 * @author Frank Appiah
 * @version 0.1
 */
public interface IEventProducer<T> extends Serializable{

    /**
     * It sends event by a defined transport communication model either a proprietary
     * protocol or standardised one like JMS, AMQP.
     * 
     * @param event object to be sent
     */
    public void sendEvent(T event);

    /**
     * It asynchronously send events by a defined transport communication model either a proprietary
     * protocol or standardised one like JMS, AMQP.
     * @param event
     */
    public void sendEventAsync(T event);
    /**
     * It routes an event to defined registered 
     * @param event Event to be routed
     */
    public void routeEvent(T event);

    /**
     *It asynchronously route an event to defined registered
     * @param event Event to be routed
     */
    public void routeEventAsync(T event);
    /**
     * It returns the details of the event producer.
     * 
     * @return event producer detail.
     */
    public IEventProducerDetail getProductDetail();

    /**
     * It sets the detail with the output terminal
     * @param eventProducerDetail Detail to set.
     */
    public void setProducerDetail(IEventProducerDetail eventProducerDetail);

    /**
     * It sets the output terminal of the event producer.
     * 
     * @param outputTerminal output terminal to be set.
     */
    public void setOutputTerminals(List<IOutputTerminal> outputTerminals);

    /**
     * It returns the output terminals of the event producer. It can be implicitly
     * or explicitly be attached to a channel. An event producer emits events
     * through these output terminals. Each output terminal has one or more event
     * types associated with it, and it also has a number of targets- references
     * to entities that receive events that are emitted through the terminal.
     * 
     * @return list of output terminals.
     */
    public List<IOutputTerminal> getOutputTerminals();
}
