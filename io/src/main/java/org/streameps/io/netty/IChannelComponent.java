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
package org.streameps.io.netty;

import java.util.List;
import java.util.Map;
import org.jboss.netty.channel.Channel;

/**
 * An interface for the managing a channel component.
 * 
 * @author  Frank Appiah
 */
public interface IChannelComponent {

    /**
     * It adds a channel to the list of channels.
     * @param channel A channel to be added to the component.
     */
    public void addChannel(Channel channel);

    /**
     * It adds a channel to the list of channels.
     * @param channel A channel to be added to the component.
     */
    public void addChannelIfAbsent(Channel channel);

    /**
     * It removes a channel from the list of channels.
     * @param channel A channel to be added to the component.
     */
    public void removeChannel(Channel channel);

    /**
     * It returns a map of identifier-channel pair of the component.
     * @return A map consisting of a unique identifier and a channel pair.
     */
    public Map<String, Channel> getChannelMap();

    /**
     * It returns all the channels in the component.
     * @return A list of channels.
     */
    public List<Channel> getChannelList();

    /**
     * It sets the name of this channel component.
     * @param name The name of the channel component.
     */
    public void setName(String name);

    /**
     * It returns the name of this channel component.
     * @return The name of the channel component.
     */
    public String getName();
}
