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
import org.jboss.netty.channel.ChannelHandlerContext;
import org.streameps.core.util.IDUtil;
import org.streameps.engine.IAggregateContext;
import org.streameps.io.netty.ChannelComponent;
import org.streameps.io.netty.EPSChannelWrite;
import org.streameps.io.netty.EPSThreadExecutor;
import org.streameps.io.netty.HandlerType;
import org.streameps.io.netty.IAggregateRequest;
import org.streameps.io.netty.IAggregateResponse;
import org.streameps.io.netty.IServiceEvent;
import org.streameps.io.netty.ServiceEvent;
import org.streameps.io.netty.ServiceType;
import org.streameps.io.netty.server.AbstractServerHandler;
import org.streameps.io.netty.server.EPSRuntimeService;
import org.streameps.io.netty.server.IEPSChannelWrite;
import org.streameps.io.netty.server.IServiceCallback;
import org.streameps.io.netty.server.listener.EPSAggregateListener;
import org.streameps.logger.ILogger;
import org.streameps.logger.LoggerUtil;
import org.streameps.processor.AggregatorListener;
import org.streameps.thread.IWorkerCallable;

/**
 *
 * @author Frank Appiah
 */
public class AggregateService extends AbstractServerHandler implements IServiceCallback<IAggregateResponse>, IAggregateService {

    private AggregatorListener aggregateListener;
    private IAggregateResponse aggregateResponse;
    private IAggregateRequest aggregateRequest;
    private ILogger logger = LoggerUtil.getLogger(AggregateService.class);

    public AggregateService() {
        aggregateListener = new EPSAggregateListener(this);
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
                aggregateRequest = (IAggregateRequest) getMessageEvent();
                IAggregateContext context = aggregateRequest.getAggregateContext();

                EPSRuntimeService.setAggregateContext(context, aggregateListener);
                logger.info("Servicing the request " + aggregateRequest.getIdentifier()
                        + " from " + getMessageEvent().getRemoteAddress().toString());
                return aggregateRequest.getIdentifier();
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

                logger.info("Handling the response:"+ " of the aggregate request ID:= "
                        + getAggregateResponse().getIdentifier());

                ChannelHandlerContext context = getHandlerContext();
                IServiceEvent event = new ServiceEvent();
                event.setHandlerType(HandlerType.RESPONSE);
                event.setServiceType(ServiceType.AGGREGATE);

                getAggregateResponse().setServiceEvent(event);
                getAggregateResponse().setRequestID(aggregateRequest.getIdentifier());
                IEPSChannelWrite write = new EPSChannelWrite();
                write.write(ChannelComponent.getInstance().getChannelList(), aggregateResponse);
               
                return getAggregateResponse().getIdentifier();
            }
        });
    }

    public void onServiceCall(IAggregateResponse value, boolean isMatchFilterSet) {
        this.aggregateResponse = value;
        handleResponse();
    }

    public IAggregateResponse getAggregateResponse() {
        return aggregateResponse;
    }

    public AggregatorListener getAggregatorListener() {
        return this.aggregateListener;
    }
}
