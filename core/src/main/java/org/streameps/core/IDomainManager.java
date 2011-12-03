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
package org.streameps.core;

import org.streameps.agent.IAgentManager;
import org.streameps.filter.IFilterManager;
import org.streameps.processor.IPatternManager;
import org.streameps.thread.IEPSExecutorManager;

/**
 * Interface for the domain manager.
 *
 * @author  Frank Appiah
 */
public interface IDomainManager {

    /**
     * It sets the pattern manager.
     * @param patternManager An instance of the pattern manager.
     */
    public void setPatternManager(IPatternManager patternManager);

    /**
     * It returns the pattern manager.
     * @return An instance of the pattern manager.
     */
    public IPatternManager getPatternManager();

    /**
     * It sets the agent manager.
     * @param agentManager  The instance of the agent manager.
     */
    public void setAgentManager(IAgentManager agentManager);

    /**
     * It returns the agent manager.
     * @return The instance of the agent manager.
     */
    public IAgentManager getAgentManager();

    /**
     * It sets the filter manager.
     * @param filterManager The instance of the filter manager.
     */
    public void setFilterManager(IFilterManager filterManager);

    /**
     * It returns the filter manager.
     * @return The instance of the filter manager.
     */
    public IFilterManager getFilterManager();

    /**
     * It return the event processing system thread executor manager.
     * @return IEPSExecutorManager
     */
    public IEPSExecutorManager getExecutorManager();

    /**
     * It sets the event processing system thread executor manager.
     * @param executorManager IEPSExecutorManager
     */
    public void setExecutorManager(IEPSExecutorManager executorManager);
}
