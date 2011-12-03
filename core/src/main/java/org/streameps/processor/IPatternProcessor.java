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

import java.util.EventListener;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.core.IParticipantEventSet;
import org.streameps.processor.pattern.IBasePattern;
import org.streameps.processor.pattern.listener.IPatternMatchListener;
import org.streameps.processor.pattern.listener.IPatternUnMatchListener;

/**
 * Interface for a pattern processor.
 *
 * @author  Frank Appiah
 */
public interface IPatternProcessor<T> extends EventListener{

    /**
     * It processes the participant event set to determine the pattern based on the
     * specified pattern algorithm.
     *
     * @param eventSet A participant event set.
     * @param basePattern A pattern algorithm.
     */
    public void process(IParticipantEventSet<T> eventSet, IBasePattern<T> basePattern);

    /**
     * It processes the accumulator sorted event to determine the pattern based on the
     * specified pattern algorithm.
     *
     * @param eventSet A sorted accumulator.
     * @param basePattern A pattern algorithm.
     */
    public void process(ISortedAccumulator<T> eventSet, IBasePattern<T> basePattern);

    /**
     * It sets the un-matched pattern listener.
     * @param unMatchListener A pattern listener.
     */
    public void setUnMatchListener(IPatternUnMatchListener<T> unMatchListener);

    /**
     * It sets the matched pattern listener.
     * @param matchListener  A pattern listener.
     */
     public void setMatchListener(IPatternMatchListener<T> matchListener) ;
}
