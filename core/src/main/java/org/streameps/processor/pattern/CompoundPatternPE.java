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
package org.streameps.processor.pattern;

import java.util.concurrent.LinkedBlockingQueue;
import org.streameps.core.IEventUpdateListener;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.IUnMatchedEventSet;
import org.streameps.core.MatchedEventSet;
import org.streameps.core.UnMatchedEventSet;
import org.streameps.dispatch.Dispatchable;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;
import org.streameps.processor.pattern.listener.UnMatchEventMap;

/**
 *
 * @author Frank Appiah
 */
public class CompoundPatternPE<E> extends BasePattern<E> implements
        IPatternMatchListener<E>,
        IPatternUnMatchListener<E>,
        IEventUpdateListener<E> {

    private BasePattern<E> pattern_1 = null, pattern_2 = null;
    private Dispatchable dispatcher;
    private MatchedEventSet<E> matchSet_1, matchSet_2;
    private UnMatchedEventSet unMatchedSet_1, unMatchedSet_2;
    private boolean isMatched = false, isUnMatched = false;
    private IEventUpdateListener<E> eventUpdateListener;
    private E[] oldMatchedEvents, newMatchedEvents;

    public CompoundPatternPE() {
        matchSet_1 = new MatchedEventSet<E>();
        matchSet_2 = new MatchedEventSet<E>();
    }

    public CompoundPatternPE(BasePattern<E> pattern_1, BasePattern<E> pattern_2) {
        this.pattern_1 = pattern_1;
        this.pattern_2 = pattern_2;
        matchSet_1 = new MatchedEventSet<E>();
        matchSet_2 = new MatchedEventSet<E>();
        unMatchedSet_1 = new UnMatchedEventSet();
        unMatchedSet_2 = new UnMatchedEventSet();
    }

    public void processEvent(E event) {
        this.participantEvents.add(event);
        this.pattern_1.processEvent(event);
    }

    public void output() {
        this.pattern_1.output();
        this.matchingSet.addAll(pattern_1.getMatchingSet());
        //pattern process 2:
        if (pattern_2 != null) {
            executePattern(pattern_2, pattern_1.getMatchingSet());
            if (pattern_2.getMatchingSet().size() > 0) {
                this.matchingSet.addAll(pattern_2.getMatchingSet());
                sendMatchedEvents(this.matchingSet);
            }
        } else {
            sendMatchedEvents(pattern_1.getMatchingSet());
        }

        if (unMatchedSet_2.size() > 0 || unMatchedSet_1.size() > 0) {
            IUnMatchEventMap<E> unmatchEventMap = new UnMatchEventMap<E>(false);
            buildUnMatchedSet(unmatchEventMap, unMatchedSet_1);
            buildUnMatchedSet(unmatchEventMap, unMatchedSet_2);
            publishUnMatchEvents(unmatchEventMap, dispatcher, getOutputStreamName());
        }

    }

    public void setEventUpdateListener(IEventUpdateListener<E> eventUpdateListener) {
        this.eventUpdateListener = eventUpdateListener;
    }

    private void sendMatchedEvents(IMatchedEventSet<E> eventSet) {
        IMatchEventMap<E> matchEventMap = new MatchEventMap<E>(false);
        for (E mEvent : eventSet) {
            matchEventMap.put(mEvent.getClass().getName(), mEvent);
        }
        publishMatchEvents(matchEventMap, dispatcher, getOutputStreamName());
    }

    private void executePattern(BasePattern<E> basePattern,
            IMatchedEventSet<E> matchedEventSet) {
        if (matchedEventSet != null) {
            for (E event : matchedEventSet) {
                basePattern.processEvent(event);
            }
        }
        basePattern.output();
    }

    private void buildUnMatchedSet(IUnMatchEventMap<E> unmatchEventMap, IUnMatchedEventSet<E> eventSet) {
        for (E mEvent : eventSet) {
            unmatchEventMap.put(mEvent.getClass().getName(), mEvent);
        }
    }

    public void onMatch(IMatchEventMap<E> eventMap, Dispatchable dispatcher, Object... optional) {
        for (String eventKey : eventMap.getKeySet()) {
            LinkedBlockingQueue<E> queue = eventMap.getMatchingEventAsObject(eventKey);
            for (E event : queue) {
                matchSet_2.add(event);
            }
        }

        onEventUpdate(oldMatchedEvents, newMatchedEvents, true);
    }

    public void onUnMatch(IUnMatchEventMap<E> eventMap, Dispatchable dispatcher, Object... optional) {
        for (String eventKey : eventMap.getKeySet()) {
            LinkedBlockingQueue<E> queue = eventMap.getUnMatchingEventAsObject(eventKey);
            for (E event : queue) {
                unMatchedSet_1.add(event);
            }
        }

        onEventUpdate(oldMatchedEvents, newMatchedEvents, false);
    }

    @Override
    public void reset() {
        matchSet_1.clear();
        matchSet_2.clear();
        unMatchedSet_1.clear();
        unMatchedSet_2.clear();
        this.participantEvents.clear();
        this.matchingSet.clear();
    }

    /**
     * This is a compound pattern process that listens to the matched
     * event set.
     * @param oldEvents The first matched event set.
     * @param newEvents The second matched event set.
     */
    public void onEventUpdate(E[] oldEvents, E[] newEvents, Object... optional) {
        if (eventUpdateListener != null) {
            eventUpdateListener.onEventUpdate(oldEvents, newEvents, optional);
        }
    }
}
