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

import io.s4.dispatcher.Dispatcher;
import io.s4.processor.AbstractPE;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.streameps.core.MatchingEventSet;
import org.streameps.core.ParticipantEventSet;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.PatternMatchListener;
import org.streameps.processor.pattern.listener.PatternUnMatchListener;
import org.streameps.processor.pattern.policy.PatternPolicy;

/**
 * EPA Book Based: Abstraction of a pattern matching signature
 */
public abstract class BasePattern extends AbstractPE {

    /**
     * A label that determines the meaning and intention of the pattern and
     * specifies the particular kind of matching function to be used.
     */
    protected String name;
    protected static String RELATION = "relation";
    protected static String VALUE = "value";
    /**
     * This is used in the matched event map for the pattern match listeners.
     * If it is null then the class name of the event it used as a replacement.
     */
    protected String eventName = null;
    /**
     * Additional values used in the definition of the pattern function.
     */
    protected List<PatternParameter> parameters = new ArrayList<PatternParameter>();
    /**
     * The participant event types list is a list of event types, and it forms part
     * of the pattern matching function. The order of these event types has
     * importance for some pattern functions.
     */
    protected ParticipantEventSet participantEvents = new ParticipantEventSet();
    /**
     * A named parameter that disambiguates the semantics of the pattern and the
     * pattern matching process.
     */
    protected List<PatternPolicy> patternPolicies = new ArrayList<PatternPolicy>();
    /**
     * A match event set
     */
    protected MatchingEventSet matchingSet = new MatchingEventSet();
    protected Logger logger = Logger.getLogger(BasePattern.class);
    /**
     * A list of matched pattern listeners.
     */
    protected List<PatternMatchListener> matchListeners = new ArrayList<PatternMatchListener>();
    /**
     * A list of un-match pattern listeners
     */
    protected List<PatternUnMatchListener> unMatchListeners = new ArrayList<PatternUnMatchListener>();

    /**
     * @param parameter
     *            the parameter to set
     */
    public void setParameter(List<PatternParameter> parameter) {
        this.parameters = parameter;
    }

    /**
     * @param participantEvents
     *            the participantEvents to set
     */
    public void setParticipantEvents(ParticipantEventSet participantEvents) {
        this.participantEvents = participantEvents;
    }

    /**
     * It sets the pattern policies for this pattern match agent.
     * @param patternPolicies List of pattern policies.
     */
    public void setPatternPolicies(List<PatternPolicy> patternPolicies) {
        this.patternPolicies = patternPolicies;
    }

    /**
     * It sets the name of this pattern.
     * @param type  Name to pattern.
     */
    public void setName(String type) {
        this.name = type;
    }

    /**
     * It sets the pattern match listeners.
     * 
     * @param matchListeners List of pattern match listeners.
     */
    public void setMatchListeners(List<PatternMatchListener> matchListeners) {
        this.matchListeners = matchListeners;
    }

    /**
     * It sets the pattern un-match listeners.
     * 
     * @param unMatchListeners List of pattern un-match listeners.
     */
    public void setUnMatchListeners(List<PatternUnMatchListener> unMatchListeners) {
        this.unMatchListeners = unMatchListeners;
    }

    /**
     * This method is called to publish all events in the matching set
     * if there are matched participant event sets.
     * 
     * @param eventMap  A map of match events
     * @param dispatcher An event dispatcher object
     */
    protected void publishMatchEvents(IMatchEventMap eventMap, Dispatcher dispatcher) {
        if (matchListeners.size() > 0) {
            for (PatternMatchListener listener : matchListeners) {
                listener.onMatch(eventMap, dispatcher);
            }
        }
    }

    /**
     * This method is called to publish all events in the participant set
     * if there are un-matched participant event sets.
     *
     * @param eventMap A map of un-match events
     * @param dispatcher An event dispatcher object
     */
    protected void publishUnMatchEvent(IMatchEventMap eventMap, Dispatcher dispatcher) {
        if (unMatchListeners.size() > 0) {
            for (PatternUnMatchListener listener : unMatchListeners) {
                listener.onUnMatch(eventMap, dispatcher);
            }
        }
    }

    /**
     *
     * @param event
     */
    public abstract void processEvent(Object event);
}
