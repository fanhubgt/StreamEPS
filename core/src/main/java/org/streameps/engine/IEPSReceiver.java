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

import java.util.List;
import org.streameps.context.IContextPartition;
import org.streameps.epn.channel.IEventChannelManager;

/**
 * It manages the channel connecting the sources to the engine. It implements the
 * transport protocol adopted by the engine to move information around the network.
 * It also acts as a de-multiplexer, receiving incoming items from multiple sources
 * and sending them, one by one, to the next component in the engine flow.
 * 
 * @author  Frank Appiah
 * @version 0.3.0
 */
public interface IEPSReceiver<C extends IContextPartition, E> {

    /**
     * It sends events received from event producers.
     *
     * @param event event received from an event producer.
     */
    public void onReceive(E event);

    /**
     * It routes events to the particular event channel.
     *
     * @param event event to route.
     * @param context A routing context for the EPS engine.
     */
    public void routeEvent(E event, IReceiverPair<? extends IRouterContext, ? extends IReceiverContext> receiverPair);

    /**
     * It pushes the list of context partitions to the EPS Decider.
     * @param partitions The list of context partitions.
     */
    public void pushContextPartition(List<C> partitions);

    /**
     * It sets the context partition.
     * @param partitions A list of context partition.
     */
    public void setContextPartitions(List<C> partitions);

    /**
     * It builds the context partition for the receiver.
     * @param receiverContext  A receiver context detail.
     * @param events A list of events.
     */
    public void buildContextPartition(IReceiverContext receiverContext, List<E> events);

    /**
     *  It builds the context partition for the receiver.
     * @param receiverContext A receiver context detail.
     */
    public void buildContextPartition(IReceiverContext receiverContext);

    /**
     * It returns the context partition.
     * @return A context partition.
     */
    public List<C> getContextPartitions();

    /**
     * It sets the context detail used to build the context partition.
     * @param contextDetail A context detail.
     */
    public void setReceiverContext(IReceiverContext receiverContext);

    /**
     * It returns the context detail used to build the context partition.
     * @return A context detail.
     */
    public IReceiverContext getReceiverContext();

    /**
     * It sets the external clock used for the receiver.
     * @param clock clock to set.
     */
    public void setClock(IClock clock);

    /**
     * It returns an external clock.
     * @return An external clock.
     */
    public IClock getClock();

    /**
     * It sets event channel for managing the input and output channels.
     * @param channel The event channel manager being set to the receiver.
     */
    public void setChannelManager(IEventChannelManager channel);

    /**
     * It returns the event channel for managing the input and output channels.
     * @return event channel An instance of the channel manager.
     */
    public IEventChannelManager getChannelManager();

    /**
     * It sets the engine processor for the channel manager.
     * @param engine An instance of the IEPSEngine.
     */
    public void setEPSEngine(IEPSEngine engine);

    /**
     * It returns the worker event queue.
     * @return A worker event queue.
     */
    public IWorkerEventQueue getEventQueue();

    /**
     * It returns the worker event queue.
     * @param eventQueue A worker event queue.
     */
    public void setEventQueue(IWorkerEventQueue eventQueue);

    /**
     * It returns the engine processor.
     * @return An instance of the IEPSEngine.
     */
    public IEPSEngine getEPSEngine();

    /**
     * It sets the history store of the receiver.
     * @param historyStore The history store.
     */
    public void setHistoryStore(IHistoryStore historyStore);

    /**
     * It returns the history store of the receiver.
     * @return The history store.
     */
    public IHistoryStore getHistoryStore();

    /**
     * It sets the EPS decider for the receiver;
     * @param decider An EPS decider;
     */
    public void setDecider(IEPSDecider decider);

    /**
     * It returns the EPS decider for the receiver.
     * @return An EPS decider;
     */
    public IEPSDecider getDecider();
}
