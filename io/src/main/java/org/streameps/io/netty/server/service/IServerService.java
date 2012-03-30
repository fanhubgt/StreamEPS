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

import org.streameps.io.netty.server.IEventReceiverHandler;

/**
 * Interface for the server service repository.
 *
 * @author  Frank Appiah
 */
public interface IServerService {

    /**
     * It sets the pattern service of the server service repository.
     * @param patternService The pattern service for the server service repository.
     */
    public void setPatternService(IPatternService patternService);

    /**
     * It returns the pattern service of the server service repository.
     * @return The pattern service for the server service repository.
     */
    public IPatternService getPatternService();

    /**
     * It sets the filter service of the server service repository.
     * @param filterService The filter service for the server service repository.
     */
    public void setFilterService(IFilterService filterService);

    /**
     * It returns the filter service of the server service repository.
     * @return The filter service for the server service repository.
     */
    public IFilterService getFilterService();

    /**
     * It sets the store service of the server service repository.
     * @param storeService The store service for the server service repository.
     */
    public void setStoreService(IStoreService storeService);

    /**
     * It returns the store service of the server service repository.
     * @return The store service for the server service repository.
     */
    public IStoreService getStoreService();

    /**
     * It sets the aggregate service of the server service repository.
     * @param aggregateService The aggregate service of the server service repository.
     */
    public void setAggregateService(IAggregateService aggregateService);

    /**
     * It returns the aggregate service of the server service repository.
     * @return The aggregate service of the server service repository.
     */
    public IAggregateService getAggregateService();

    /**
     * It sets the configuration service of the server service repository.
     * @param configurationService The configuration service of the server service repository.
     */
    public  void setConfigurationService(IConfigurationService configurationService);

    /**
     * It sets the configuration service of the server service repository.
     * @return The configuration service of the server service repository.
     */
    public IConfigurationService getConfigurationService();

    /**
     * It returns the decider service of the server service repository.
     * @return The decider service of the server service repository.
     */
    public IDeciderServiceCallback getDeciderService();

    /**
     * It sets the decider service of the server service repository.
     * @param serviceCallback The decider service of the server service repository.
     */
    public void setDeciderService(IDeciderServiceCallback serviceCallback);

    /**
     * It sets the event receiver handler for the server service.
     * @param eventReceiverHandler the event receiver handler for the server service.
     */
    public void setEventReceiverHandler(IEventReceiverHandler eventReceiverHandler);

    /**
     * It returns the event receiver handler for the server service.
     * @return the event receiver handler for the server service.
     */
    public IEventReceiverHandler getEventReceiverHandler();
}
