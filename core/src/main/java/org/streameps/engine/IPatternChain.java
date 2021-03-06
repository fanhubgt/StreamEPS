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
import java.util.List;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.core.IEventUpdateListener;
import org.streameps.context.IPartitionWindow;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;

/**
 * It provides a chain execution of patterns in the order in which is was added
 * to the pattern chain.
 * For example, count the number of events instances from a stream, then apply
 * any patterns to the collected events after successive process.
 * 
 * @author  Frank Appiah
 */
public interface IPatternChain<B extends IBasePattern> {

    /**
     * It adds a new pattern to the pattern chain to be executed.
     * @param pattern An instance of pattern to be added to the chain.
     */
    public void addPattern(B pattern);

    /**
     * It removes a pattern from the patten chain.
     * @param pattern An instance of pattern to be removed.
     */
    public void removePattern(B pattern);

    /**
     * It runs all the patterns in the chain in the order in which was added.
     */
    public void executePatternChain(IPartitionWindow<ISortedAccumulator> partitionWindow);

    /**
     * It sets an indicator to show whether it is a multiple pattern.
     * @param isMultiplePatterned
     */
    public void setMultiplePatterned(boolean isMultiplePatterned);

    /**
     * It returns the list of patterns in the chain.
     * @return The list of patterns in the chain.
     */
    public ArrayDeque<B> getPatterns();

    /**
     * It sets the patterns of this chain of patterns.
     * @param arrayDeque The patterns in this chain of patterns.
     */
    public void setPatterns(ArrayDeque<B> arrayDeque);

    /**
     * It adds a pattern matched listener for chain pattern execution.
     * @param matchListener A pattern match listener.
     */
    public void addPatternMatchedListener(IPatternMatchListener<?> matchListener);

    /**
     * It removes a pattern matched listener for chain pattern execution.
     * @param matchListener A pattern match listener.
     */
    public void removePatternMatchedListener(IPatternMatchListener<?> matchListener);

    /**
     * It adds a pattern matched listener for chain pattern execution.
     * @param matchListener A pattern match listener.
     */
    public void addPatternUnMatchedListener(IPatternUnMatchListener<?> matchListener);

    /**
     * It removes a pattern matched listener for chain pattern execution.
     * @param matchListener A pattern match listener.
     */
    public void removePatternUnMatchedListener(IPatternUnMatchListener<?> matchListener);

    /**
     * It adds an event update listener to listen to updates of events during the
     * execution process.
     * @param eventUpdateListener An event update listener.
     */
    public void addEventUpdateListener(IEventUpdateListener eventUpdateListener);

    /**
     * An indicator that there are three or more pattern detectors.
     * @return A boolean indicator.
     */
    public boolean isMultiplePatterned();

    public IBasePattern getBasePattern();

    /**
     * It returns the list of match listeners.
     * @return  The list of match listeners.
     */
    public List<IPatternMatchListener<?>> getMatchListeners();

    /**
     * It returns the list of un-match listeners.
     * @return The list of un-match listeners.
     */
    public List<IPatternUnMatchListener<?>> getUnMatchListeners();

    /**
     * It returns the event update listeners for the pattern chain.
     *
     * @param eventUpdateListeners The event update listeners for the pattern chain.
     */
    public List<IEventUpdateListener> setEventUpdateListeners();

    /**
     * It sets the event update listeners for the pattern chain.
     * 
     * @param eventUpdateListeners The event update listeners for the pattern chain.
     */
    public void setEventUpdateListeners(List<IEventUpdateListener> eventUpdateListeners);

    /**
     * It set the list of pattern match listeners.
     * @param matchListeners The list of pattern match listeners.
     */
    public void setMatchListeners(List<IPatternMatchListener<?>> matchListeners);

    /**
     * It set the list of un-match pattern listeners.
     * @param unMatchListeners The list of un-match pattern listeners.
     */
    public void setUnMatchListeners(List<IPatternUnMatchListener<?>> unMatchListeners);
}
