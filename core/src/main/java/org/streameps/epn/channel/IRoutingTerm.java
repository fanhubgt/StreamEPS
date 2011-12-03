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

package org.streameps.epn.channel;

/**
 * Interface for the  routing predicate term.
 * 
 * @author Frank Appiah
 */
public interface IRoutingTerm<T> {

    /**
     * It sets the property name for the event instance.
     * @param propertyName Property name of event.
     */
    public void setPropertyName(String propertyName);

    /**
     * It returns the property name of event instance.
     * @return property name.
     */
    public String getPropertyName();

    /**
     * It sets the predicate operator for the expression term.
     * Supported Predicate Relation:
     *  LessThan - <
     *  GreaterThan - >
     *  LessThanOrEqual - <=
     *  GreaterThanOrEqual - >=
     *  Equal - =
     * @param predicateOperator Operator used for comparison.
     */
    public void setPredicateOperator(String predicateOperator);

    /**
     * It returns the operator being used for the comparison.
     * @return Predicate Operator.
     */
    public String getPredicateOperator();

    /**
     * It sets the property value for the predicate term.
     * 
     * @param value property value for the predicate term.
     */
    public void setPropertyValue(T value);

    /**
     * It returns the property value for the predicate term.
     * 
     * @return Property value
     */
    public T getPropertyValue();
}
