/*
 * ====================================================================
 *  SoftGene Technologies
 * 
 *  (C) Copyright 2012.
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

package org.streameps.epn.channel;

import java.util.ArrayList;
import java.util.List;
import org.streameps.core.IEventType;

/**
 *
 * @author Frank Appiah
 */
public class MultiChannelInputTerminal<T> implements ChannelInputTerminal<T>{

    private String identifier;
    private List<ChannelInputTerminal<T>> channelInputTerminals;
    private List<IEventType<T>> eventTypes;

    public MultiChannelInputTerminal() {
        channelInputTerminals=new ArrayList<ChannelInputTerminal<T>>();
    }

    public MultiChannelInputTerminal(String identifier) {
        this.identifier = identifier;
         channelInputTerminals=new ArrayList<ChannelInputTerminal<T>>();
    }

    public String getIdentifier() {
       return this.identifier;
    }

    public void setEventTypes(List<IEventType<T>> eventTypes) {
        this.eventTypes=eventTypes;
    }

    public List<IEventType<T>> getEventTypes() {
        return this.eventTypes;
    }

    public void receiveEvent(T event, boolean asynch) {
       for(ChannelInputTerminal<T> terminal:getChannelInputTerminals()){
           terminal.setEventTypes(eventTypes);
           terminal.receiveEvent(event, asynch);
       }
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setChannelInputTerminals(List<ChannelInputTerminal<T>> channelInputTerminals) {
        this.channelInputTerminals = channelInputTerminals;
    }

    public List<ChannelInputTerminal<T>> getChannelInputTerminals() {
        return channelInputTerminals;
    }

}
