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
import org.streameps.engine.IFilterContext;

/**
 * An interface for a filter request to the server.
 * This is process by the filter service.
 * 
 * @author  Frank Appiah
 */
public interface IFilterRequest extends Serializable, IEPSRequest{

    /**
     * It sets the filter context for the filter request.
     * @param context The filter context for the filter request.
     */
    public void setFilterContext(IFilterContext context);

    /**
     * It returns the filter context for the filter request.
     * @return The filter context for the filter request.
     */
    public IFilterContext getFilterContext();

    /**
     * It returns the identifier for the filter request.
     * @return The identifier for the filter request.
     */
    public String getIdentifier();

    /**
     * It sets the identifier for the filter request.
     * @param identifier The identifier for the filter request.
     */
    public void setIdentifier(String identifier);

    /**
     * It sets the filter interest of the filter request.
     * @param filterInterest The instance of the filter interest.
     */
    public void setFilterInterest(MatchInterest filterInterest);

    /**
     * It returns the filter interest of the filter request.
     * @return The instance of the filter interest.
     */
    public MatchInterest getFilterInterest();
}
