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
import java.util.Set;
import org.streameps.core.util.IDUtil;
import org.streameps.engine.IPatternChain;
import org.streameps.io.netty.ChannelComponent;
import org.streameps.io.netty.EPSChannelWrite;
import org.streameps.io.netty.EPSStatus;
import org.streameps.io.netty.EPSThreadExecutor;
import org.streameps.io.netty.HandlerType;
import org.streameps.io.netty.IPatternRequest;
import org.streameps.io.netty.IPatternResponse;
import org.streameps.io.netty.IServiceEvent;
import org.streameps.io.netty.PatternResponse;
import org.streameps.io.netty.ServiceEvent;
import org.streameps.io.netty.ServiceType;
import org.streameps.io.netty.server.AbstractServerHandler;
import org.streameps.io.netty.server.EPSRuntimeService;
import org.streameps.io.netty.server.IEPSChannelWrite;
import org.streameps.io.netty.server.IServiceCallback;
import org.streameps.io.netty.server.listener.PatternMatchListener;
import org.streameps.io.netty.server.listener.PatternUnMatchListener;
import org.streameps.thread.IWorkerCallable;

/**
 * It is a service used to handle all pattern computations from the client.
 * On a match/un-match events, the service listener will then response to the
 * client with the serialisable set of events.
 * 
 * @author Frank Appiah
 */
public class PatternService extends AbstractServerHandler implements IPatternService, IServiceCallback<Set<Object>> {

    private PatternMatchListener serviceListener;
    private PatternUnMatchListener unMatchListener;
    private IPatternResponse patternResponse;
    private IPatternRequest patternRequest;
    private Set<Object> matchEvents, unMatchEvents;
    private boolean isMatchEventSet = false;

    public PatternService() {
        serviceListener = new PatternMatchListener(this);
        unMatchListener = new PatternUnMatchListener(this);
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
                patternRequest = (IPatternRequest) getMessageEvent().getMessage();
                IPatternChain patternChain = patternRequest.getPatternChain();
                patternChain.getMatchListeners().add(serviceListener);
                patternChain.getUnMatchListeners().add(unMatchListener);
                EPSRuntimeService.setPatternContext(patternChain);
                return patternRequest.getIdentifier();
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
                patternResponse = new PatternResponse(IDUtil.getUniqueID(new Date().toString()));

                IServiceEvent event = new ServiceEvent(ServiceType.PATTERN, HandlerType.RESPONSE);
                patternResponse.setServiceEvent(event);
                patternResponse.setStatus(EPSStatus.COMPLETE);
                if (isMatchEventSet) {
                    patternResponse.setPatternEvents(matchEvents);
                    patternResponse.setMatchEvent(isMatchEventSet);
                } else {
                    patternResponse.setPatternEvents(unMatchEvents);
                    patternResponse.setMatchEvent(isMatchEventSet);
                }
                IEPSChannelWrite write = new EPSChannelWrite();
                write.write(ChannelComponent.getInstance().getChannelList(), patternResponse);
                return patternResponse.getIdentifier();
            }
        });
    }

    public IPatternResponse getPatternResponse() {
        return this.patternResponse;
    }

    public PatternMatchListener getPatternServiceListener() {
        return this.serviceListener;
    }

    public void setPatternServiceListener(PatternMatchListener serviceListener) {
        this.serviceListener = serviceListener;
    }

    public void onServiceCall(Set<Object> value, boolean isMatchFilterSet) {
        if (isMatchFilterSet) {
            matchEvents = value;
        } else {
            unMatchEvents = value;
        }
        isMatchEventSet = isMatchFilterSet;
        handleResponse();
    }

    public PatternUnMatchListener getPatternUnMatchListener() {
        return this.unMatchListener;
    }

    public void setPatternUnMatchListener(PatternUnMatchListener serviceListener) {
        this.unMatchListener = serviceListener;
    }
}
