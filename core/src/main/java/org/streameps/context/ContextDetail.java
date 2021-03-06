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
package org.streameps.context;

import java.util.Date;
import org.streameps.core.util.IDUtil;

/**
 * Implementation of the context detail spec.
 * 
 * @author Frank Appiah
 */
public class ContextDetail implements IContextDetail {

    private String identifier;
    private ContextDimType contextDimType;
    private IContextInitiatorPolicy policy;

    public ContextDetail() {
    }

    public ContextDetail(String identifier, ContextDimType contextDimType) {
        this.identifier = identifier;
        this.contextDimType = contextDimType;
    }

    public ContextDetail( String contextDimType) {
        this.contextDimType = ContextDimType.valueOf(contextDimType);
        identifier=IDUtil.getUniqueID(new Date().toString());
    }
    
    public ContextDetail(String identifier, String contextDimType) {
        this.identifier = identifier;
        this.contextDimType = ContextDimType.valueOf(contextDimType);
    }

    public ContextDetail(String identifier, ContextDimType contextDimType, IContextInitiatorPolicy policy) {
        this.identifier = identifier;
        this.contextDimType = contextDimType;
        this.policy = policy;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setContextDimension(ContextDimType contextDimType) {
        this.contextDimType = contextDimType;
    }

    public ContextDimType getContextDimension() {
        return this.contextDimType;
    }

    public void setContextInitiatorPolicy(IContextInitiatorPolicy policy) {
        this.policy = policy;
    }

    public IContextInitiatorPolicy getContextInitiatorPolicy() {
        return this.policy;
    }
}
