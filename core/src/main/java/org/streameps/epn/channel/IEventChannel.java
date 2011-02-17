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
package org.streameps.epn.channel;

import java.util.List;

/**
 * An event channel is a processing element that receives events from one or
 * more source processing elements, makes routing decisions, and sends the
 * input events unchanged to one or more target processing elements in
 * accordance with these routing decisions.
 * 
 * @author  Frank Appiah
 */
public interface IEventChannel {

    /**
     * It sets the name of the channel described by the definition element. 
     * It can be used to refer to this definition element from elsewhere.
     * @param identifier name of channel
     */
    public void setChannelIdentifier(String identifier);

    /**
     * It returns the name of the channel described by the definition
     * element. It can be used to refer to this definition element from elsewhere.
     * @return channel identifier.
     */
    public String getChannelIdentifier();

    /**
     * This specifies the type of information used when making routing decisions.
     * 
     * @param routingScheme routing scheme
     */
    public void setRoutingScheme(IRoutingScheme routingScheme);

    /**
     * It returns the type of information used when making routing decisions.
     * 
     * @return routing scheme
     */
    public IRoutingScheme getRoutingScheme();

    /**
     * It the channel terminals used for outputting/routing an event instance to
     * other processing channels.
     * 
     * @param terminals list of terminals to set.s
     */
    public void setChannelOutputTerminals(List<ChannelOutputTerminal> terminals);

    /**
     * It returns a list of output terminals for the channel.
     * 
     * @return list of output terminals.
     */
    public List<ChannelOutputTerminal> getChannelOutputTerminals();

    /**
     * It sets the channel input terminals.
     * 
     * @param terminals list of input terminals
     */
    public void setChannelInputTerminals(List<ChannelInputTerminal> terminals);

    /**
     * It returns the list of channel input terminals.
     * 
     * @return list of input terminals
     */
    public List<ChannelInputTerminal> getChannelInputTerminals();
}
