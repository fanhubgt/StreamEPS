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
import org.streameps.core.IOutputTerminal;
import org.streameps.epn.channel.ChannelOutputTerminal;

/**
 * Interface for the event processing forwarder.
 * It forwards the event stream to the client's output terminal from the EPS
 * producer after the filter, combine and aggregate process.
 * 
 * @author  Frank Appiah
 */
public interface IEPSForwarder<T> {

    /**
     * It sets the list of output terminal which acts as the sink.
     * @param outputTerminal A list of output terminal.
     */
    public void setOutputTerminals(List<IOutputTerminal> outputTerminal);

    /**
     * It returns the list of output terminal.
     * @return A list of output terminal.
     */
    public List<IOutputTerminal> getOutputTerminals();

    /**
     * It receives a filter context from the EPS producer.
     * @param filterContext A filter context.
     */
    public void onContextReceive(IFilterContext<T> forwardContext);

    public void onPatternContextReceive(IDeciderContext deciderContext);

    public void onAggregateContextReceive(IAggregateContext aggregateContext);

    /**
     * It forwards the value set to the output terminals.
     */
    public void forwardToOutputTerminals();

    /**
     * It sets the channel output terminal.
     * @param channelOutputTerminal A channel output terminal.
     */
    public void setChannelOutputTerminal(ChannelOutputTerminal channelOutputTerminal);

    /**
     * It returns the channel output terminal.
     * @return A channel output terminal.
     */
    public ChannelOutputTerminal getChannelOutputTerminal();
}
