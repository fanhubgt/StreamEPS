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

import org.streameps.core.IMatchedEventSet;
import org.streameps.core.IParticipantEventSet;
import org.streameps.core.IUnMatchedEventSet;
import org.streameps.engine.IPatternChain;
import org.streameps.processor.pattern.IBasePattern;

/**
 * Interface for the pattern process manager.
 *
 * @author  Frank Appiah
 */
public interface IPatternManager<T> {

    /**
     * It processes the pattern of a set of events.
     * 
     * @param basePattern pattern base being used for the computation.
     */
    public void processPattern(IBasePattern<T> basePattern, IParticipantEventSet<T> participantEventSet);

    /**
     * It processes the pattern of a set of events.
     *
     * @param basePattern pattern base being used for the computation.
     */
    public void processPatternChain(IPatternChain basePattern, IParticipantEventSet<T> participantEventSet);

    /**
     * It returns the pattern for the manager to detect the matched event set.
     * 
     * @return The pattern event detector.
     */
    public IBasePattern<T> getPattern();

    /**
     * It returns the pattern chain.
     * @return An instance of a pattern chain.
     */
    public IPatternChain getPatternChain();

    /**
     * It returns the matched event set after the processPattern function.
     * @return An instance of the matched event set.
     */
    public IMatchedEventSet<T> getMatchedEventSet();

    /**
     * It returns the unmatched event set.
     * @return An instance of the unmatched event set.
     */
    public IUnMatchedEventSet<T> getUnMatchedEventSet();

    /**
     * It returns the participation event set for the stream.
     * @return The instance of the participation event set.
     */
    public IParticipantEventSet<T> getParticipationEventSet();
}
