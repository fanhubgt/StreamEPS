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

import java.io.Serializable;

/**
 * Interface for the context detail specification.
 * 
 * @author  Frank Appiah
 */
public interface IContextDetail extends Serializable{

    /**
     * It sets the name of the context specification described by the definition
     * element. It can be used to refer to this definition element from elsewhere.
     * @param identifier  It is the context identifier for the context detail.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns the name of the context specification described by the definition
     * element.
     * @return The context identifier for the context detail.
     */
    public String getIdentifier();

    /**
     * It sets the context dimension for this context details.
     * @param contextDimType Context Dimension type.
     */
    public void setContextDimension(ContextDimType contextDimType);

    /**
     * It returns the context dimension
     * <p>
     * Supported Types:
     * <ul>
     *  <li> Temporal</li>
     *  <li> Segment</li>
     *  <li> State-oriented </li>
     *  <li> composite </li>
     *  <li> spatial </li>
     * </ul>
     * </p>
     */
    public ContextDimType getContextDimension();

    /**
     * It sets the context initiator policy.
     * 
     * @param policy context initiator policy to set.
     */
    public void setContextInitiatorPolicy(IContextInitiatorPolicy policy);

    /**
     * It returns the context initiator policy.
     *
     * @return context initiator policy.
     */
    public IContextInitiatorPolicy  getContextInitiatorPolicy();
}
