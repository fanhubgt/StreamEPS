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

import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.jboss.netty.channel.Channel;
import org.streameps.core.EPSRuntimeClient;
import org.streameps.core.IPayload;
import org.streameps.core.IStreamEvent;
import org.streameps.engine.AuditEventStore;
import org.streameps.engine.HistoryStore;
import org.streameps.engine.IEPSEngine;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.IStoreContext;
import org.streameps.engine.StoreContext;
import org.streameps.engine.StoreType;
import org.streameps.io.netty.EPSCommandLineProp;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.preference.IPreference;
import org.streameps.preference.PreferenceInstance;
import org.streameps.store.IStoreProperty;
import org.streameps.store.StoreProperty;
import org.streameps.store.file.IEPSFileSystem;
import org.streameps.store.file.IFileEPStore;
import org.streameps.thread.IWorkerCallable;

/**
 *
 * @author Frank Appiah
 */
public class EventReceiverHandler extends AbstractServerHandler implements IEventReceiverHandler {

    private ILogger logger = LoggerUtil.getLogger(EventReceiverHandler.class);
    private IEPSEngine engine;
    private Set<IStreamEvent> streamEvents;
    private long counter = 0;

    public EventReceiverHandler() {
        streamEvents = new HashSet<IStreamEvent>();
    }

    public void handleRequest() {
        Object mesg = getMessageEvent().getMessage();
        Channel channel = getMessageEvent().getChannel();
        SocketAddress address = getMessageEvent().getChannel().getRemoteAddress();
        boolean sent = false;
        String engineIdentifier = null;

        if (mesg instanceof IStreamEvent) {
            IStreamEvent streamEvent = (IStreamEvent) mesg;
            streamEvents.add(streamEvent);
            IPayload payload = streamEvent.getPayload();
            if (EPSRuntimeService.isEnginePerClient()) {
                try {
                    Map<String, IEngineChannel> map = EPSRuntimeService.getEngineChannelMap();
                    for (IEngineChannel iec : map.values()) {
                        Channel ch = iec.getChannel();
                        engineIdentifier = iec.getIdentifier();
                        if (ch.getId() == channel.getId()) {
                            iec.getEngine().sendEvent(payload.getEvent(), iec.getEngine().isAsynchronous());
                            sent = true;
                            break;
                        }
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            } else {
                if (engine == null) {
                    while (PreferenceInstance.getInstance().hasNext()) {
                        IPreference preference = PreferenceInstance.getInstance().next();
                        engine = preference.getRuntimePreference().getEngine();
                        if (engine != null) {
                            break;
                        }
                    }
                    logger.info("Engine preference loaded: " + engine.toString());
                    try {
                        EPSRuntimeService.createInstance(engine, address.toString());
                    } catch (RuntimeServiceException ex) {
                        logger.warn(ex.getMessage());
                    }
                    EPSRuntimeClient.getInstance().setEngine(engine);
                } else {
                    engine.sendEvent(payload.getEvent(), engine.isAsynchronous());
                }
            }
            logger.info("[Receiver Handler] : Handling the event received [Size:" + (++counter) + "]....");
        }
    }

    public void handleResponse() {
    }

    public Set<IStreamEvent> getStreamEvents() {
        return streamEvents;
    }

    public void addStreamEvent(IStreamEvent streamEvent) {
        TimeUnit timeUnit = TimeUnit.valueOf(System.getProperty("timeUnit"));
        if (timeUnit == null) {
            timeUnit = TimeUnit.SECONDS;
        }
        long time = Long.parseLong(System.getProperty("persistStreamTimestamp"));
        if (time < 0) {
            time = 30;
        }
        engine.getExecutorManager().executeAtFixedRate(new IWorkerCallable<Boolean>() {

            public String getIdentifier() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void setIdentifier(String name) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Boolean call() throws Exception {
                int eventSaveCount = 10;
                if (System.getProperty("eventSaveCount") != null) {
                    eventSaveCount = Integer.parseInt(System.getProperty("eventSaveCount"));
                }
                if (streamEvents.size() >= eventSaveCount) {
                    String location = System.getProperty(EPSCommandLineProp.STORE_LOCATION);
                    location += "/streams";
                    IStoreProperty isp = new StoreProperty("comp", IEPSFileSystem.DEFAULT_SYSTEM_ID, location);
                    IHistoryStore historyStore = new HistoryStore(StoreType.FILE,
                            new AuditEventStore(isp, engine.getExecutorManager()));
                    synchronized (streamEvents) {
                        IStoreContext<Set<IStreamEvent>> context = new StoreContext<Set<IStreamEvent>>(streamEvents);
                        ((AuditEventStore) historyStore).saveStream(IFileEPStore.STREAMS, context);
                        streamEvents = new HashSet<IStreamEvent>();
                    }
                }
                return Boolean.TRUE;
            }
        }, 1, time, timeUnit);
    }
}
