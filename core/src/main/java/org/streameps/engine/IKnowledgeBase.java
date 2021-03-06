/*
 * ====================================================================
 *  StreamEPS Platform
 *  (C) Copyright 2011
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
 *  =============================================================================
 */
package org.streameps.engine;

import java.util.List;

/**
 * Interface for the knowledge base.
 * 
 * @author  Frank Appiah
 */
public interface IKnowledgeBase {

    /**
     * It sets the event processing system producer which
     * sends events to the forwarder to forward events to the
     * output terminal of the client.
     * @param producer An EPS producer.
     */
    public void setProducer(IEPSProducer producer);

    /**
     * It sets the event processing system pattern decider.
     * @param decider A pattern decider.
     */
    public void setDecider(IEPSDecider decider);

    /**
     * It returns the event processing system pattern decider.
     * @return An instance of the decider.
     */
    public IEPSDecider getDecider();

    /**
     * It returns the event processing system producer.
     * @return An instance of the producer.
     */
    public IEPSProducer getProducer();

    /**
     * It sets the list of rule base context.
     * @param rule A list of rule context.
     */
    public void setRuleContexts(List<IRuleBase> rule);

    /**
     * It returns the list of rule base context.
     * @return A list of rule context.
     */
    public List<IRuleBase> getRuleContexts();

    /**
     * It receives the decider context from the EPS decider and then
     * adds to the knowledge base.
     *
     * @param deciderContext A decider context.
     */
    public void onDeciderContextReceive(IDeciderContext deciderContext);

    public void onFilterContextReceive(IFilterContext filterContext);

    public void onAggregateContextReceive(IAggregateContext aggregateContext);
    
    /**
     * It sends the decider context to the engine for further/no processing.
     */
    public void sendDeciderContext();

    /**
     * It sets the EPS Engine for the knowledge base.
     * @param engine An EPS Engine.
     */
    public void setEPSEngine(IEPSEngine engine);
}
