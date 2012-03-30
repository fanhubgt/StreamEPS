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
import java.util.List;

/**
 * Interface for the context entry specification.
 * 
 * @author  Frank Appiah
 */
public interface IContextEntry extends Serializable{

    /**
     * It sets the event type for the context entry.
     * 
     * @param type event type
     */
    public void setEventType(String type);

    /**
     * It returns the event type for the entry.
     * 
     * @return event type.
     */
    public String getEventType();

    /**
     * It sets the predicate expression for the entry.
     * 
     * @param predicateExpr Predicate expression.
     */
    public void setPredicateExpr(IPredicateExpr predicateExpr);

    /**
     * It returns the predicate expression for the event parameter.
     * 
     * @return predicate expression.
     */
    public IPredicateExpr getPredicateExpr();

    /**
     * It sets the predicate term for the context entry.
     * @param predicateTerm The predicate term.
     */
    public void setPredicateTerms(List<IPredicateTerm> predicateTerm);

    /**
     * It returns the predicate term for the context entry.
     * @return The predicate term.
     */
    public List<IPredicateTerm> getPredicateTerms();
}
