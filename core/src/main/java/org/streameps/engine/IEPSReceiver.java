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
import org.streameps.epn.channel.IEventChannelManager;
import org.streameps.processor.pattern.IBasePattern;

/**
 * It manages the channel connecting the sources to the engine. It implements the
 * transport protocol adopted by the engine to move information around the network.
 * It also acts as a de-multiplexer, receiving incoming items from multiple sources
 * and sending them, one by one, to the next component in the engine flow.
 * 
 * @author  Frank Appiah
 * @version 0.3.0
 */
public interface IEPSReceiver<C extends IContextPartition, B extends IBasePattern> {

    /**
     * It sends events received from event producers.
     *
     * @param event event received from an event producer.
     */
    public void sendOnReceive(Object event);

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
     * It sets the pattern decider processor for the receiver.
     * @param decider An instance of the decider.
     */
    public void setDecider(IEPSDecider<C, B> decider);

    /**
     * It returns the pattern decider processor for the receiver.
     * @return An instance of the pattern decider.
     */
    public IEPSDecider<C, B> getDecider();
}
