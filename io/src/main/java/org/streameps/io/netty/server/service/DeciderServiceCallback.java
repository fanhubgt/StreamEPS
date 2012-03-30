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
import org.streameps.io.netty.ChannelComponent;
import org.streameps.io.netty.EPSChannelWrite;
import org.streameps.io.netty.EPSThreadExecutor;
import org.streameps.io.netty.HandlerType;
import org.streameps.io.netty.IDeciderRequest;
import org.streameps.io.netty.IDeciderResponse;
import org.streameps.io.netty.IServiceEvent;
import org.streameps.io.netty.ServiceEvent;
import org.streameps.io.netty.ServiceType;
import org.streameps.io.netty.server.AbstractServerHandler;
import org.streameps.io.netty.server.IEPSChannelWrite;
import org.streameps.io.netty.server.listener.DeciderContextListener;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.thread.IWorkerCallable;

/**
 *
 * @author Frank Appiah
 */
public class DeciderServiceCallback extends AbstractServerHandler implements IDeciderServiceCallback {

    private DeciderContextListener deciderContextListener;
    private IDeciderResponse deciderResponse;
    private IDeciderRequest request;
    private ILogger logger = LoggerUtil.getLogger(DeciderServiceCallback.class);

    public DeciderServiceCallback() {
        deciderContextListener = new DeciderContextListener(this);
    }

    public DeciderServiceCallback(DeciderContextListener contextListener) {
        this.deciderContextListener = contextListener;
    }

    public void onServiceCall(IDeciderResponse value, boolean isMatchFilterSet) {
        this.deciderResponse = value;
            handleResponse();
    }

    public void handleRequest() {
        EPSThreadExecutor.createInstance().execute(new IWorkerCallable<String>() {

            public String getIdentifier() {
                return IDUtil.getUniqueID(new Date().toString());
            }

            public void setIdentifier(String name) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public String call() throws Exception {
                request = (IDeciderRequest) getMessageEvent().getMessage();
                return request.getIdentifier();
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
                    IServiceEvent event = new ServiceEvent(ServiceType.DECIDER, HandlerType.RESPONSE);
                    deciderResponse.setServiceEvent(event);
                    IEPSChannelWrite write = new EPSChannelWrite();
                    write.write(ChannelComponent.getInstance().getChannelList(), deciderResponse);
                
                return request.getIdentifier();
            }
        });

    }

    public DeciderContextListener getDeciderContextListener() {
        return deciderContextListener;
    }

    public void setDeciderContextListener(DeciderContextListener deciderContextListener) {
        this.deciderContextListener = deciderContextListener;
    }
}
