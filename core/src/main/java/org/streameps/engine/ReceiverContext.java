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

package org.streameps.engine;

import org.streameps.context.IContextDetail;
import org.streameps.context.IContextParam;
import org.streameps.context.IPredicateTerm;

/**
 *
 * @author Frank Appiah
 */
public class ReceiverContext implements IReceiverContext{

    private IContextParam contextParam;
    private IContextDetail contextDetail;
    private String identifier;
    private IPredicateTerm predicateTerm;
    private String attribute;

    public ReceiverContext() {
    }

    public ReceiverContext(IContextParam contextParam, IContextDetail contextDetail,
            String identifier, IPredicateTerm predicateTerm) {
        this.contextParam = contextParam;
        this.contextDetail = contextDetail;
        this.identifier = identifier;
        this.predicateTerm = predicateTerm;
    }

    public ReceiverContext(IContextParam contextParam, IContextDetail contextDetail,
            String identifier, IPredicateTerm predicateTerm, String attribute) {
        this.contextParam = contextParam;
        this.contextDetail = contextDetail;
        this.identifier = identifier;
        this.predicateTerm = predicateTerm;
        this.attribute = attribute;
    }

    public void setContextParam(IContextParam contextParam) {
        this.contextParam=contextParam;
    }

    public IContextParam getContextParam() {
       return this.contextParam;
    }

    public void setContextDetail(IContextDetail contextDetail) {
        this.contextDetail=contextDetail;
    }

    public IContextDetail getContextDetail() {
       return this.contextDetail;
    }

    public void setIdentifier(String identifier) {
        this.identifier=identifier;
    }

    public String getIdentifier() {
       return this.identifier;
    }

    public void setPredicateTerm(IPredicateTerm predicateTerm) {
        this.predicateTerm=predicateTerm;
    }

    public IPredicateTerm getPredicateTerm() {
       return this.predicateTerm;
    }

    public void setAttribute(String attribute) {
        this.attribute=attribute;
    }

    public String getAttribute() {
        return this.attribute;
    }

}
