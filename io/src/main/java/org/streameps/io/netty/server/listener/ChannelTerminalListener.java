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
package org.streameps.io.netty.server.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.streameps.core.util.IDUtil;
import org.streameps.dispatch.DispatcherService;
import org.streameps.dispatch.IDispatcherService;
import org.streameps.engine.IWorkerEventQueue;
import org.streameps.epn.channel.ChannelOutputTerminal;
import org.streameps.io.netty.server.ServerEventQueue;

/**
 *
 * @author Frank Appiah
 */
public class ChannelTerminalListener implements ChannelOutputTerminal<Object> {

    private List<String> eventTypes;
    private IWorkerEventQueue workerEventQueue;
    private int querySize = 10;
    private IDispatcherService dispatcherService;

    public ChannelTerminalListener() {
        eventTypes = new ArrayList<String>();
        workerEventQueue = new ServerEventQueue(querySize);
        dispatcherService = new DispatcherService();
        workerEventQueue.setDispatcherService(dispatcherService);
    }

    public String getIdentifier() {
        return IDUtil.getUniqueID(new Date().toString());
    }

    public void setEventTypes(List eventTypes) {
        this.eventTypes = eventTypes;
    }

    public List getEventTypes() {
        return this.eventTypes;
    }

    public void sendEvent(Object event, boolean asynch) {
        String eventName = event.getClass().getName();
        if (!getEventTypes().contains(eventName)) {
            getEventTypes().add(eventName);
        }
        ((ServerEventQueue) workerEventQueue).setEventTypes(eventTypes);
        workerEventQueue.addToQueue(event, eventName);
    }
}
