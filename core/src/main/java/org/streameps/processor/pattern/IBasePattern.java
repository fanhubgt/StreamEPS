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
package org.streameps.processor.pattern;

import java.io.Serializable;
import java.util.List;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.IParticipantEventSet;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;
import org.streameps.processor.pattern.policy.PatternPolicy;
import org.streameps.thread.IEPSExecutorManager;

/**
 * Base structure for a pattern match signature.
 * 
 * @author Frank Appiah
 */
public interface IBasePattern<E> extends Serializable{

    /**
     * It returns a list of pattern match listeners
     * @return pattern match listeners
     */
    public List<IPatternMatchListener<E>> getMatchListeners();

    /**
     * It returns a list of pattern parameter for the matching processing.
     * @return list of pattern match
     */
    public List<IPatternParameter<E>> getParameters();

    /**
     * It returns a list of pattern un-match listeners.
     * @return list of un-match listeners
     */
    public List<IPatternUnMatchListener<E>> getUnMatchListeners();

    /**
     * It starts the matching process.
     * @param event event used for matching
     */
    public void processEvent(E event);

    /**
     * It sets the pattern match listeners.
     *
     * @param matchListeners List of pattern match listeners.
     */
    public void setMatchListeners(List<IPatternMatchListener<E>> matchListeners);

    /**
     * It sets the name of this pattern.
     * @param type  Name to pattern.
     */
    public void setPatternType(String type);

    /**
     * It sets the list of pattern parameter.
     * 
     * @param parameter the parameter to set
     */
    public void setParameters(List<IPatternParameter<E>> parameter);

    /**
     * @param participantEvents
     * the participantEvents to set
     */
    public void setParticipantEvents(IParticipantEventSet<E> participantEvents);

    /**
     * It sets the pattern policies for this pattern match agent.
     * @param patternPolicies List of pattern policies.
     */
    public void setPatternPolicies(List<PatternPolicy> patternPolicies);

    /**
     * It return the pattern policies for this pattern match agent.
     * @param patternPolicies A List of pattern policies.
     */
    public List<PatternPolicy> getPatternPolicies();

    /**
     * It sets the pattern un-match listeners.
     *
     * @param unMatchListeners List of pattern un-match listeners.
     */
    public void setUnMatchListeners(List<IPatternUnMatchListener<E>> unMatchListeners);

    /**
     * It is called to output matched and un-matched events from the stream of events.
     */
    public void output();

    public IParticipantEventSet<E> getParticipantEvents();

    public IMatchedEventSet<E> getMatchingSet();

    public void reset();

    /**
     * It sets the executor manager for the base pattern.
     * @param executorManager the executor manager for the base pattern.
     */
    public void setExecutorManager(IEPSExecutorManager executorManager);

    /**
     * It returns the executor manager  for the base pattern.
     * @return The executor manager for the base pattern.
     */
    public IEPSExecutorManager getEPSExecutorManager();
}
