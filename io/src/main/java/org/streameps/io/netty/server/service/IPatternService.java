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
package org.streameps.io.netty.server.service;

import org.streameps.io.netty.IPatternResponse;
import org.streameps.io.netty.server.IServerChannelHandler;
import org.streameps.io.netty.server.listener.PatternMatchListener;
import org.streameps.io.netty.server.listener.PatternUnMatchListener;

/**
 *
 * @author  Frank Appiah
 */
public interface IPatternService extends IServerChannelHandler {

    /**
     * It returns the pattern response of the service.
     * @return The pattern response of the service.
     */
    public IPatternResponse getPatternResponse();

    /**
     * It returns the pattern service listener for the pattern service.
     * @return The pattern service listener for the pattern service.
     */
    public PatternMatchListener getPatternServiceListener();

    /**
     * It sets the pattern service listener for the pattern service.
     * @param serviceListener The pattern service listener for the pattern service.
     */
    public void setPatternServiceListener(PatternMatchListener serviceListener);

      /**
     * It returns the pattern service listener for the pattern service.
     * @return The pattern service listener for the pattern service.
     */
    public PatternUnMatchListener getPatternUnMatchListener();

    /**
     * It sets the pattern service listener for the pattern service.
     * @param serviceListener The pattern service listener for the pattern service.
     */
    public void setPatternUnMatchListener(PatternUnMatchListener serviceListener);
}
