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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.client.IEventUpdateListener;
import org.streameps.context.IPartitionWindow;
import org.streameps.core.IParticipantSetWrapper;
import org.streameps.core.ParticipantSetWrapper;
import org.streameps.dispatch.Dispatchable;
import org.streameps.processor.IPatternProcessor;
import org.streameps.processor.PatternProcessor;
import org.streameps.processor.pattern.BasePattern;
import org.streameps.processor.pattern.CompoundPatternPE;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.listener.IMatchEventMap;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;
import org.streameps.processor.pattern.listener.IUnMatchEventMap;
import org.streameps.processor.pattern.listener.MatchEventMap;

/**
 *
 * @author Frank Appiah
 */
public class PatternChain<B extends BasePattern> implements IPatternChain<B>,
        IEventUpdateListener,
        IPatternMatchListener,
        IPatternUnMatchListener {

    private ArrayDeque<B> tempChain, chain;
    private List<IPatternMatchListener<?>> matchListeners;
    private List<IPatternUnMatchListener<?>> unMatchListeners;
    private boolean isMultiplePatterned = false, temp = false;
    private List<IEventUpdateListener> eventUpdateListeners;
    private IBasePattern basePattern;
    private Object[] newMatchedEvents, newUnMatchedEvents;
    private Dispatchable dispatch;
    private IMatchEventMap eventMap;

    public PatternChain() {
        matchListeners = new ArrayList<IPatternMatchListener<?>>();
        unMatchListeners = new ArrayList<IPatternUnMatchListener<?>>();
        eventUpdateListeners = new ArrayList<IEventUpdateListener>();
        tempChain = new ArrayDeque<B>();
        chain = new ArrayDeque<B>();
        eventMap = new MatchEventMap(false);
    }

    public void addPattern(B pattern) {
        this.chain.add(pattern);
    }

    public void removePattern(B pattern) {
        this.chain.remove(pattern);
    }

    public ArrayDeque<B> getPatterns() {
        return this.chain;
    }

    public void executePatternChain(IPartitionWindow<ISortedAccumulator> partitionWindow) {
        int i = chain.size();
        tempChain = chain;
        switch (i) {
            case 1:
                executeSingePattern(partitionWindow);
                break;
           // case 2:
            //   executeCompoundPattern(partitionWindow);
            //    break;
            default:
                executeMultiplePatterns(partitionWindow);
                break;
        }
    }

    private void executeSingePattern(IPartitionWindow<ISortedAccumulator> partitionWindow) {
        basePattern = tempChain.poll();
        IParticipantSetWrapper participantSetWrapper = new ParticipantSetWrapper(partitionWindow.getWindow());
        IPatternProcessor patternProcessor = new PatternProcessor(this, this);
        patternProcessor.process(participantSetWrapper.getParticipantEventSet(), basePattern);
        tempChain = chain;
    }

    private void executeCompoundPattern(IPartitionWindow<ISortedAccumulator> partitionWindow) {
        IBasePattern compoundPatternPE = new CompoundPatternPE(tempChain.poll(), tempChain.poll());
        IParticipantSetWrapper participantSetWrapper = new ParticipantSetWrapper(partitionWindow.getWindow());
        IPatternProcessor patternProcessor = new PatternProcessor(this, this);
        patternProcessor.process(participantSetWrapper.getParticipantEventSet(), compoundPatternPE);
        tempChain = chain;
    }

    private void executeMultiplePatterns(IPartitionWindow<ISortedAccumulator> partitionWindow) {
        int size = chain.size();
        isMultiplePatterned = true;
        temp = true;
        ISortedAccumulator accumulator = partitionWindow.getWindow();
        String keyName = null;
        IMatchEventMap eventMapTemp = new MatchEventMap(false);

        for (int i = 0; i < size - 1; i++) {
            basePattern = tempChain.poll();
            // basePattern.setEventUpdateListener(this);
            IParticipantSetWrapper participantSetWrapper = new ParticipantSetWrapper(accumulator);
            IPatternProcessor patternProcessor = new PatternProcessor(this, this);
            patternProcessor.process(participantSetWrapper.getParticipantEventSet(), basePattern);
            if (basePattern.getMatchingSet().size() > 0) {
                accumulator.clear();
                for (Object event : basePattern.getMatchingSet()) {
                    accumulator.processAt(event.getClass().getName(), event);
                }
            }
        }
        isMultiplePatterned = false;
        if (eventMapTemp.getKeySet().size() > 0) {
            this.onMatch(eventMapTemp, dispatch, isMultiplePatterned);
        }
        tempChain = chain;
    }

    public void addPatternMatchedListener(IPatternMatchListener<?> matchListener) {
        this.matchListeners.add(matchListener);
    }

    public void removePatternMatchedListener(IPatternMatchListener<?> matchListener) {
        this.matchListeners.remove(matchListener);
    }

    public void addPatternUnMatchedListener(IPatternUnMatchListener<?> unmatchListener) {
        this.unMatchListeners.add(unmatchListener);
    }

    public void removePatternUnMatchedListener(IPatternUnMatchListener<?> unmatchListener) {
        this.unMatchListeners.remove(unmatchListener);
    }

    public void setDispatch(Dispatchable dispatch) {
        this.dispatch = dispatch;
    }

    public void onMatch(IMatchEventMap eventMap, Dispatchable dispatcher, Object... optional) {
        if (isMultiplePatterned) {
            this.eventMap.merge(eventMap);
            return;
        }
        if (this.matchListeners != null) {
            for (IPatternMatchListener listener : this.matchListeners) {
                listener.onMatch(eventMap, dispatcher, optional);
            }
        }
    }

    public void onUnMatch(IUnMatchEventMap eventMap, Dispatchable dispatcher, Object... optional) {
        if (this.unMatchListeners != null) {
            for (IPatternUnMatchListener listener : this.unMatchListeners) {
                listener.onUnMatch(eventMap, dispatcher, optional);
            }
        }
    }

    public void onEventUpdate(Object[] oldEvents, Object[] newEvents, Object... optional) {
        if (this.eventUpdateListeners != null) {
            for (IEventUpdateListener ieul : this.eventUpdateListeners) {
                ieul.onEventUpdate(oldEvents, newEvents, optional);
            }
        }
        newMatchedEvents = newEvents;
    }

    public void addEventUpdateListener(IEventUpdateListener eventUpdateListener) {
        this.eventUpdateListeners.add(eventUpdateListener);
    }

    public void setIsMultiplePatterned(boolean isMultiplePatterned) {
        this.isMultiplePatterned = isMultiplePatterned;
    }

    public boolean isMultiplePatterned() {
        return this.temp;
    }
}
