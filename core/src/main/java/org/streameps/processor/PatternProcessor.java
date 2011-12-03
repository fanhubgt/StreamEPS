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

import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.core.IParticipantEventSet;
import org.streameps.core.IParticipantSetWrapper;
import org.streameps.core.ParticipantSetWrapper;
import org.streameps.dispatch.Dispatchable;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;

/**
 *
 * @author Frank Appiah
 */
public class PatternProcessor<T> implements IPatternProcessor<T>, IPatternMatchListener<T>, IPatternUnMatchListener<T> {

    private IPatternMatchListener<T> matchListener;
    private IPatternUnMatchListener<T> unMatchListener;

    public PatternProcessor(IPatternMatchListener<T> matchListener, IPatternUnMatchListener<T> unMatchListener) {
        this.matchListener = matchListener;
        this.unMatchListener = unMatchListener;
    }

    public void process(IParticipantEventSet<T> eventSet, IBasePattern<T> basePattern) {
        basePattern.getMatchListeners().add(this);
        basePattern.getUnMatchListeners().add(this);
        for (T event : eventSet) {
            basePattern.processEvent(event);
        }
        basePattern.output();
    }

    public void process(ISortedAccumulator<T> eventSet, IBasePattern<T> basePattern) {
        basePattern.getMatchListeners().add(this);
        basePattern.getUnMatchListeners().add(this);
        IParticipantSetWrapper<T> wrapper = new ParticipantSetWrapper<T>(eventSet);
        for (T event : wrapper.getParticipantEventSet()) {
            basePattern.processEvent(event);
        }
        basePattern.output();
    }

    public void onMatch(IMatchEventMap<T> eventMap, Dispatchable dispatcher, Object... optional) {
        this.matchListener.onMatch(eventMap, dispatcher, optional);
    }

    public void onUnMatch(IUnMatchEventMap<T> eventMap, Dispatchable dispatcher, Object... optional) {
        this.unMatchListener.onUnMatch(eventMap, dispatcher, optional);
    }

    public void setUnMatchListener(IPatternUnMatchListener<T> unMatchListener) {
        this.unMatchListener = unMatchListener;
    }

    public void setMatchListener(IPatternMatchListener<T> matchListener) {
        this.matchListener = matchListener;
    }
}
