/*
 * ====================================================================
 *  StreamEPS Platform
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
package org.streameps.io.netty.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.jboss.netty.channel.Channel;
import org.streameps.core.util.IDUtil;
import org.streameps.dispatch.Dispatchable;
import org.streameps.engine.IReceiverContext;
import org.streameps.engine.WorkerEventQueue;
import org.streameps.io.netty.ChannelComponent;
import org.streameps.io.netty.EPSChannelWrite;
import org.streameps.io.netty.ITerminalResponse;
import org.streameps.io.netty.TerminalResponse;

/**
 *
 * @author Frank Appiah
 */
public class ServerEventQueue<T> extends WorkerEventQueue<T> {

    private int querySize = 10;
    private Channel chanel;
    private List<String> eventTypes;

    public ServerEventQueue() {
        super();
        eventTypes = new ArrayList<String>();
    }

    public ServerEventQueue(int queueSize) {
        super(queueSize);
        this.querySize = queueSize;
        eventTypes = new ArrayList<String>();
    }

    @Override
    public void addDispatchable() {
        if (getDispatcherService() != null) {
            getDispatcherService().registerDispatcher(new Dispatchable() {

                private ITerminalResponse response;

                public void dispatch() {
                    List list = null;
                    for (String event : getEventTypes()) {
                        list = poll(querySize, event);
                        if (list.size() < querySize) {
                        } else if (list.size() == querySize) {
                            Set set = new TreeSet(list);
                            response = new TerminalResponse(IDUtil.getUniqueID(new Date().toString()), set);
                            IEPSChannelWrite write = new EPSChannelWrite();
                            write.write(ChannelComponent.getInstance().getChannelList(), response);
                        }
                    }
                   removeList(list);
                }
            });
        }
    }

    @Override
    public void buildContextPartition(IReceiverContext receiverContext) {
    }

    public int getQuerySize() {
        return querySize;
    }

    public void setChanel(Channel chanel) {
        this.chanel = chanel;
    }

    public Channel getChanel() {
        return chanel;
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }
}
