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
 * Default implementation for event channel processing element.
 * 
 * @author Frank Appiah
 */
public class EventChannel implements IEventChannel{

    private String identifier;
    private IRoutingScheme routingScheme;
    private List<ChannelInputTerminal> inputTerminals;
    private List<ChannelOutputTerminal> outputTerminals;

    public EventChannel() {
    }

    public EventChannel(String identifier, IRoutingScheme routingScheme) {
        this.identifier = identifier;
        this.routingScheme = routingScheme;
    }

    public void setChannelIdentifier(String identifier) {
        this.identifier=identifier;
    }

    public String getChannelIdentifier() {
        return this.identifier;
    }

    public void setRoutingScheme(IRoutingScheme routingScheme) {
        this.routingScheme=routingScheme;
    }

    public IRoutingScheme getRoutingScheme() {
        return this.routingScheme;
    }

    public void setChannelOutputTerminals(List<ChannelOutputTerminal> terminals) {
        this.outputTerminals=terminals;
    }

    public List<ChannelOutputTerminal> getChannelOutputTerminals() {
        return this.outputTerminals;
    }

    public void setChannelInputTerminals(List<ChannelInputTerminal> terminals) {
        this.inputTerminals=terminals;
    }

    public List<ChannelInputTerminal> getChannelInputTerminals() {
        return this.inputTerminals;
    }

    public void addChannelInputTerminal(ChannelInputTerminal cit)
    {
        inputTerminals.add(cit);
    }

    public void addChannelOutputTerminal(ChannelOutputTerminal cot)
    {
        outputTerminals.add(cot);
    }

}
