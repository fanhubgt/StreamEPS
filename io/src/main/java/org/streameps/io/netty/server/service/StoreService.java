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
package org.streameps.io.netty.server.service;

import java.util.Date;
import org.streameps.core.util.IDUtil;
import org.streameps.engine.AuditEventStore;
import org.streameps.engine.HistoryStore;
import org.streameps.engine.IHistoryStore;
import org.streameps.engine.StoreType;
import org.streameps.io.netty.ChannelComponent;
import org.streameps.io.netty.EPSChannelWrite;
import org.streameps.io.netty.EPSStatus;
import org.streameps.io.netty.EPSThreadExecutor;
import org.streameps.io.netty.HandlerType;
import org.streameps.io.netty.IServiceEvent;
import org.streameps.io.netty.IStoreRequest;
import org.streameps.io.netty.IStoreResponse;
import org.streameps.io.netty.ServiceEvent;
import org.streameps.io.netty.ServiceType;
import org.streameps.io.netty.StoreResponse;
import org.streameps.io.netty.server.AbstractServerHandler;
import org.streameps.io.netty.server.EPSRuntimeService;
import org.streameps.io.netty.server.IEPSChannelWrite;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.store.IStoreProperty;
import org.streameps.thread.IWorkerCallable;

/**
 *
 * @author Frank Appiah
 */
public class StoreService extends AbstractServerHandler implements IStoreService {

    private ILogger logger = LoggerUtil.getLogger(StoreService.class);
    private IStoreRequest storeRequest;
    private IStoreResponse storeResponse;
    private IStoreRequest.StoreEnum type;

    public void handleRequest() {
        EPSThreadExecutor.createInstance().execute(new IWorkerCallable<String>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String name) {
            }

            public String call() throws Exception {
                storeRequest = (IStoreRequest) getMessageEvent().getMessage();
                IStoreRequest.StoreEnum type = storeRequest.getStoreAction();
                switch (type) {
                    case CHANGE_STORE_PATH: {
                        IStoreProperty property = storeRequest.getStoreProperty();
                        StoreType st = storeRequest.getStoreType();
                        IHistoryStore historyStore = new HistoryStore(st,
                                new AuditEventStore(property, EPSThreadExecutor.createInstance()));
                        EPSRuntimeService.setStoreHistory(historyStore);
                        logger.info("[StoreService] Changing the store path of the runtime server from request: "
                                + storeRequest.getIdentifier());
                    }
                    break;
                    case DELETE_STORE: {
                    }
                    break;
                    case GET_STORE_PATH: {
                    }
                    break;
                    case SEARCH_STORE: {
                    }
                    break;
                    case LOAD_STORE: {
                    }
                    break;
                    default:
                        throw new IllegalArgumentException("The action is not supported by the service");
                }

                return storeRequest.getIdentifier();
            }
        });
    }

    public void handleResponse() {
        EPSThreadExecutor.createInstance().execute(new IWorkerCallable<String>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String name) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public String call() throws Exception {
                switch (type) {
                    case CHANGE_STORE_PATH:
                    case DELETE_STORE: {
                        IStoreResponse storeResponse = new StoreResponse(IDUtil.getUniqueID(new Date().toString()));
                        storeResponse.setStatus(EPSStatus.COMPLETE);
                        storeResponse.setRequestID(storeRequest.getIdentifier());
                        IServiceEvent event = new ServiceEvent(ServiceType.STORE, HandlerType.RESPONSE);
                        storeResponse.setServiceEvent(event);
                        IEPSChannelWrite write = new EPSChannelWrite();
                        write.write(ChannelComponent.getInstance().getChannelList(), storeResponse);
                    }
                    break;
                    case GET_STORE_PATH:
                    case SEARCH_STORE:
                    case LOAD_STORE:
                        break;
                    default:
                        throw new IllegalArgumentException("The action is not supported by the store service");
                }
                return storeResponse.getIdentifier();
            }
        });
    }

    public void setStoreRequest(IStoreRequest storeRequest) {
        this.storeRequest = storeRequest;
    }

    public IStoreRequest getStoreRequest() {
        return this.storeRequest;
    }

    public void setStoreResponse(IStoreResponse storeResponse) {
        this.storeResponse = storeResponse;
    }

    public IStoreResponse getStoreResponse() {
        return this.storeResponse;
    }
}
