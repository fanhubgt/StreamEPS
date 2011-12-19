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

import org.streameps.agent.AgentManager;
import org.streameps.agent.IAgentManager;
import org.streameps.filter.FilterManager;
import org.streameps.filter.IFilterManager;
import org.streameps.processor.IPatternManager;
import org.streameps.processor.PatternManager;
import org.streameps.thread.EPSExecutorManager;
import org.streameps.thread.IEPSExecutorManager;

/**
 *
 * @author Frank Appiah
 */
public class DomainManager implements IDomainManager {

    private IPatternManager patternManager;
    private IFilterManager filterManager;
    private IAgentManager agentManager;
    private IEPSExecutorManager executorManager;

    public DomainManager() {
        patternManager=new PatternManager();
        filterManager=new FilterManager();
        agentManager=new AgentManager();
        executorManager=new EPSExecutorManager();
    }

    public DomainManager(IPatternManager patternManager, IFilterManager filterManager, IAgentManager agentManager, IEPSExecutorManager executorManager) {
        this.patternManager = patternManager;
        this.filterManager = filterManager;
        this.agentManager = agentManager;
        this.executorManager = executorManager;
    }


    public void setPatternManager(IPatternManager patternManager) {
        this.patternManager = patternManager;
    }

    public IPatternManager getPatternManager() {
        return this.patternManager;
    }

    public void setAgentManager(IAgentManager agentManager) {
        this.agentManager = agentManager;
    }

    public IAgentManager getAgentManager() {
        return this.agentManager;
    }

    public void setFilterManager(IFilterManager filterManager) {
        this.filterManager = filterManager;
    }

    public IFilterManager getFilterManager() {
        return this.filterManager;
    }

    public IEPSExecutorManager getExecutorManager() {
        return executorManager;
    }

    public void setExecutorManager(IEPSExecutorManager executorManager) {
        this.executorManager = executorManager;
    }
    
}
