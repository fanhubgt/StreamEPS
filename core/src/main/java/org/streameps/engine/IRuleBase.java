/*
 * ====================================================================
 *  StreamEPS Platform
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
 * Interface for the processing rule used to describe filter, combine and aggregate
 * operations on events from processing stream.
 * 
 * @author  Frank Appiah
 */
public interface IRuleBase<T> {

    /**
     * It sets a unique identifier for the rule condition mapper.
     * @param name A unique identifier.
     */
    public void setIdentifier(String name);

    /**
     * It returns a unique identifier for the rule condition mapper.
     * @return A unique identifier.
     */
    public String getIdentifier();

    /**
     * It sets the event processing system decider for the rule.
     * @param decider An instance of the decider.
     */
    public void setDecider(IEPSDecider decider);

    /**
     * It returns the event processing system decider
     * @return An instance of the decider.
     */
    public IEPSDecider getDecider();

    /**
     * It loads a semantic file containing the rules for processing the
     * filter, combine and aggregate operation.
     *
     * @param file The path to the file containing
     */
    public List<IRuleContext<T>> loadRules(String repositoryURL, String file);

    /**
     * It sets the rule context for the rule processing.
     * @param actionMap An action map for the rule context.
     */
    public void setRuleContext(List<IRuleContext<T>> context);

    /**
     * It returns the rule context  for EPS rule.
     * @return An action map for the rule context.
     */
    public List<IRuleContext<T>> getRuleContext();

    /**
     * It return the knowledge base for loading the rule context.
     * @return A knowledge base.
     */
    public IRuleRepository getRuleRepository();

    /**
     * It sets the knowledge base for loading the rule context.
     * @param knowledgeBase A knowledge base.
     */
    public void setRuleRepository(IRuleRepository ruleRepository);

    /**
     * It returns the parent for the rule base.
     * @return An instance of a parent rule base.
     */
    public IRuleBase<T> getParent();
}
