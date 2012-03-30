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

import org.streameps.io.netty.server.EventReceiverHandler;
import org.streameps.io.netty.server.IEventReceiverHandler;

/**
 *
 * @author Frank Appiah
 */
public class ServerService implements IServerService {

    private IFilterService filterService;
    private IAggregateService aggregateService;
    private IPatternService patternService;
    private IStoreService storeService;
    private IConfigurationService configurationService;
    private static IServerService serverService;
    private IDeciderServiceCallback serviceCallback;
    private IEventReceiverHandler eventReceiverHandler;

    public ServerService(IFilterService filterService, IAggregateService aggregateService, IPatternService patternService, IStoreService storeService) {
        this.filterService = filterService;
        this.aggregateService = aggregateService;
        this.patternService = patternService;
        this.storeService = storeService;
    }

    public ServerService(IFilterService filterService, IAggregateService aggregateService, IPatternService patternService, IStoreService storeService, IConfigurationService configurationService) {
        this.filterService = filterService;
        this.aggregateService = aggregateService;
        this.patternService = patternService;
        this.storeService = storeService;
        this.configurationService = configurationService;
    }

    public IEventReceiverHandler getEventReceiverHandler() {
        return this.eventReceiverHandler;
    }

    public void setEventReceiverHandler(IEventReceiverHandler eventReceiverHandler) {
        this.eventReceiverHandler=eventReceiverHandler;
    }

    public ServerService() {
        filterService = new FilterService();
        patternService = new PatternService();
        aggregateService = new AggregateService();
        storeService = new StoreService();
        configurationService = new ConfigurationService();
        serviceCallback = new DeciderServiceCallback();
        eventReceiverHandler=new EventReceiverHandler();
    }

    public static IServerService getInstance() {
        if (serverService == null) {
            serverService = new ServerService();
        }
        return serverService;
    }

    public void setFilterService(IFilterService filterService) {
        this.filterService = filterService;
    }

    public IFilterService getFilterService() {
        return this.filterService;
    }

    public IAggregateService getAggregateService() {
        return this.aggregateService;
    }

    public void setAggregateService(IAggregateService aggregateService) {
        this.aggregateService = aggregateService;
    }

    public void setPatternService(IPatternService patternService) {
        this.patternService = patternService;
    }

    public IPatternService getPatternService() {
        return this.patternService;
    }

    public void setStoreService(IStoreService storeService) {
        this.storeService = storeService;
    }

    public IStoreService getStoreService() {
        return this.storeService;
    }

    public void setConfigurationService(IConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    public IConfigurationService getConfigurationService() {
        return this.configurationService;
    }

    public IDeciderServiceCallback getDeciderService() {
        return this.serviceCallback;
    }

    public void setDeciderService(IDeciderServiceCallback serviceCallback) {
        this.serviceCallback = serviceCallback;
    }
    
}
