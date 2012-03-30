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
package org.streameps.agent;

import org.streameps.core.ITargetRefSpec;
import java.util.Map;

/**
 * Interface for the agent output terminal.
 *
 * @author  Frank Appiah
 */
public interface IAgentTerminal<T> {

    /**
     * It sets the identifier for the terminal.
     * @param identifier A unique identifier.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the identifier for the terminal.
     * @return  A unique identifier.
     */
    public String getIdentifier();

    /**
     * It returns the target reference specification.
     * @return A target reference specification.
     */
    public ITargetRefSpec getTargetRefSpec();

    /**
     * It sets the target reference specification.
     * @param targetRefSpec A target reference specification.
     */
    public void setTargetRefSpec(ITargetRefSpec targetRefSpec);

    /**
     * It sets the property map for the agent.
     * @param propertyMap A property map for the agent.
     */
    public void setProperties(Map<String, String> propertyMap);

    /**
     * It returns the property map for the agent.
     * @return A property map for the agent.
     */
    public Map<String, String> getProperties();

    /**
     * It is invoke on the terminal of a distributed agent in the event processing
     * network.
     *
     * @param context A specific context(filter, aggregate, decider) for the agent.
     */
    public void invokeAgent(T context);
}
