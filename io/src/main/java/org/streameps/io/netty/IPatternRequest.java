/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  (C) Copyright 2012.
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
package org.streameps.io.netty;

import java.io.Serializable;
import org.streameps.engine.IPatternChain;
import org.streameps.processor.pattern.IBasePattern;

/**
 * Interface for a pattern request to be served by the server.
 * 
 * @author  Frank Appiah
 */
public interface IPatternRequest extends Serializable, IEPSRequest {

    /**
     * It sets the pattern chain of the request.
     * @param patternChain  The pattern chain of the request.
     */
    public void setPatternChain(IPatternChain<IBasePattern> patternChain);

    /**
     * It sets the pattern chain of the request.
     * @return The pattern chain of the request.
     */
    public IPatternChain<IBasePattern> getPatternChain();

    /**
     * It sets the identifier of the pattern request.
     * @param identifier The identifier of the pattern request.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the identifier of the pattern request.
     * @return The identifier of the pattern request.
     */
    public String getIdentifier();

    /**
     * It sets the match interest of the pattern request.
     * @param matchInterest The match interest of the pattern request.
     */
    public void setMatchInterest(MatchInterest matchInterest);

    /**
     * It returns the match interest of the pattern request.
     * @return The match interest of the pattern request.
     */
    public MatchInterest getMatchInterest();
}
