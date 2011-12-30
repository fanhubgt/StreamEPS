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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.jboss.netty.channel.Channel;

/**
 *
 * @author Frank Appiah
 */
public class ChannelComponent implements IChannelComponent {

    private Channel channel;
    private List<Channel> channels;
    private Map<String, Channel> channelMap;
    private String name;

    public ChannelComponent() {
        channels = new ArrayList<Channel>();
        channelMap = new TreeMap<String, Channel>();
    }

    public void addChannel(Channel channel) {
        channels.add(channel);
        channelMap.put(channel.getId().toString(), channel);
        this.channel=channel;
    }

    public void removeChannel(Channel channel) {
        channels.remove(channel);
        for (Entry<String, Channel> entry : channelMap.entrySet()) {
            if (channelMap.containsValue(channel)) {
                String key = entry.getKey();
                channelMap.remove(key);
                break;
            }
        }
    }

    public Map<String, Channel> getChannelMap() {
        return this.channelMap;
    }

    public List<Channel> getChannelList() {
        return this.channels;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addChannelIfAbsent(Channel channel) {
        if (channelMap.containsKey(channel.getId())) {
            return;
        } else {
            channelMap.put(channel.getId().toString(), channel);
        }
        this.channel=channel;
    }
    
}
