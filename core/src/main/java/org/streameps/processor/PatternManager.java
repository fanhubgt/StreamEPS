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
package org.streameps.processor;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.PartitionWindow;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.IParticipantEventSet;
import org.streameps.core.IUnMatchedEventSet;
import org.streameps.dispatch.Dispatchable;
import org.streameps.engine.IPatternChain;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;

/**
 *
 * @author Frank Appiah
 */
public class PatternManager<T> implements IPatternManager<T>, IPatternMatchListener<T>, IPatternUnMatchListener<T> {

    private IParticipantEventSet<T> participantEventSet = null;
    private IMatchedEventSet<T> matchedEventSet = null;
    private IUnMatchedEventSet<T> unMatchedEventSet = null;
    private IBasePattern<T> basePattern;
    private IPatternChain patternChain;

    public PatternManager() {
    }

    public void processPattern(IBasePattern<T> basePattern, IParticipantEventSet<T> participantEventSet) {
        basePattern.getMatchListeners().add(this);
        basePattern.getUnMatchListeners().add(this);
        for (T event : participantEventSet) {
            basePattern.processEvent(event);
        }
        basePattern.output();
        this.basePattern = basePattern;
        this.participantEventSet = participantEventSet;
    }

    public IMatchedEventSet<T> getMatchedEventSet() {
        return this.matchedEventSet;
    }

    public IParticipantEventSet<T> getParticipationEventSet() {
        return this.participantEventSet;
    }

    public IBasePattern<T> getPattern() {
        return this.basePattern;
    }

    public void onMatch(IMatchEventMap<T> eventMap, Dispatchable dispatcher, Object... optional) {
        Map<String, LinkedBlockingQueue<T>> matched = (Map<String, LinkedBlockingQueue<T>>) eventMap.getMatchingEvents();
        for (String key : matched.keySet()) {
            for (T object : matched.get(key)) {
                matchedEventSet.add(object);
            }
        }
    }

    public void onUnMatch(IUnMatchEventMap<T> eventMap, Dispatchable dispatcher, Object... optional) {
        Map<String, LinkedBlockingQueue<T>> matched = (Map<String, LinkedBlockingQueue<T>>) eventMap.getUnMatchingEvents();
        for (String key : matched.keySet()) {
            for (T object : matched.get(key)) {
                unMatchedEventSet.add(object);
            }
        }
    }

    public IUnMatchedEventSet<T> getUnMatchedEventSet() {
        return this.unMatchedEventSet;
    }

    public IPatternChain getPatternChain() {
        return this.patternChain;
    }

    public void processPatternChain(IPatternChain patternChain, IParticipantEventSet<T> participantEventSet) {
        this.patternChain = patternChain;
        ISortedAccumulator<T> accumulator = new SortedAccumulator<T>();
        for (T event : participantEventSet) {
            accumulator.processAt(event.getClass().getName(), event);
        }
        IPartitionWindow<ISortedAccumulator<T>> window = new PartitionWindow<ISortedAccumulator<T>>();
        window.setWindow(accumulator);
        patternChain.executePatternChain(window);
    }
    
}
