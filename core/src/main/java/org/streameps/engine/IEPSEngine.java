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
import org.streameps.core.IDomainManager;
import org.streameps.thread.IEPSExecutorManager;

/**
 * Interface for the event processing engine.
 *
 * @author  Frank Appiah
 * @version 0.3.3
 */
public interface IEPSEngine<C extends IContextPartition, E> {

    public void sendStreamEvent(E event);

    /**
     * It sets the pattern decider processor for the receiver.
     * @param decider An instance of the decider.
     */
    public void setDecider(IEPSDecider<C> decider);

    /**
     * It sets the receiver of the events from the engine.
     * @param sReceiver An instance of an EPS receiver.
     */
    public void setEPSReceiver(IEPSReceiver<C, E> sReceiver);

    /**
     * It returns the EPS receiver for the engine.
     * @return  An instance of an EPS receiver.
     */
    public IEPSReceiver<C, E> getEPSReceiver();

    /**
     * It returns the pattern decider processor for the receiver.
     * @return An instance of the pattern decider.
     */
    public IEPSDecider<C> getDecider();

    /**
     * It orders the context partition using the timestamp from the event source.
     * @param contextPartition A context partition.
     */
    public void orderContext(C contextPartition);

    /**
     * It sets the decider context received from the decider.
     * @param deciderContext A decider context from the decider.
     */
    public void onDeciderContextReceive(IDeciderContext deciderContext);

    /**
     * It sends an event to the EPS receiver asynchronously or not.
     * 
     * @param event The event to send to the receiver.
     * @param asynch An indicator whether to send event asynchronously.
     */
    public void sendEvent(E event, boolean asynch);

    /**
     * It sends an event to the EPS receiver asynchronously or not.
     *
     * @param event The event to send to the receiver.
     */
    public void sendEvent(E event);

    /**
     * It sets the domain manager for the engine.
     * @param domainManager A domain manager.
     */
    public void setDomainManager(IDomainManager domainManager);

    /**
     *It return the domain manager for the engine.
     * @return A domain manager.
     */
    public IDomainManager getDomainManager();

    /**
     * It sets the number of dispatchable processes for the dispatcher size.
     */
    public void setDispatcherSize(int size);

    /**
     * It returns the number of dispatchable processes for the dispatcher size.
     * @return the number of dispatchable processes
     */
    public int getDispatcherSize();

    /**
     * It sets the executor manager of the engine.
     * @param executorManager The thread executor manager.
     */
    public void setExecutorManager(IEPSExecutorManager executorManager);

    /**
     * It returns the executor manager of the engine.
     * @return The thread executor manager.
     */
    public IEPSExecutorManager getExecutorManager();

    /**
     * It sets the indicator to determine whether to save events on received
     * or not.
     * @param saveOnReceive An indicator whether to save or not.
     */
    public void setSaveOnReceive(boolean saveOnReceive);

    /**
     * It returns the indicator to determine whether to save events on received
     * or not.
     * @return An indicator whether to save or not.
     */
    public boolean isSaveOnReceive();

    /**
     * It sets an indicator whether to send events asynchronously or not.
     * @param asynchronous An indicator whether to send events asynchronously or not.
     */
    public void setAsynchronous(boolean asynchronous);

    /**
     * It returns an indicator whether to send events asynchronously or not.
     * @return An indicator whether to send events asynchronously or not.
     */
    public boolean isAsynchronous();
}
