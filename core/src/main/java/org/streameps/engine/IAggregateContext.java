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

import java.io.Serializable;
import java.util.List;
import org.streameps.aggregation.IAggregatePolicy;
import org.streameps.aggregation.IAggregation;
import org.streameps.operator.assertion.AssertionType;

/**
 * Interface for the aggregate context.
 * 
 * @author  Frank Appiah
 */
public interface IAggregateContext<T, E> extends Serializable {

    /**
     * It sets the unique identifier.
     * @param identifier A unique identifier.
     */
    public void setIdentifier(String identifier);

    /**
     * It returns a unique identifier.
     * @return A unique identifier.
     */
    public String getIdentifier();

    /**
     * It sets the property name used to retrieve the value from the event.
     * @param property The property name in the event.
     */
    public void setAggregateProperty(String property);

    /**
     * It returns the aggregate property name.
     * @return The property name in the event.
     */
    public String getAggregateProperty();

    /**
     * It sets the threshold used to for the comparison.
     * @param threshold The threshold value.
     */
    public void setThresholdValue(E threshold);

    /**
     * It returns the aggregation functions.
     * @param aggregation The aggregation function.
     */
    public List<IAggregation<T, E>> getAggregatorList();

    /**
     * It sets the aggregation functions.
     * @param aggregation The aggregation function.
     */
    public void setAggregatorList(List<IAggregation<T, E>> aggregation);

    /**
     * It returns the threshold used to for the comparison.
     * @return The threshold value.
     */
    public E getThresholdValue();

    /**
     * It returns the result of the aggregate context.
     * @return The aggregate result.
     */
    public E getAggregateResult();

    /**
     * It returns the result of the aggregate context.
     * @return The aggregate result.
     */
    public void setAggregateResult(E result);

    /**
     * It sets the policy for the aggregate context.
     * @param aggregatePolicy The aggregate policy.
     */
    public void setPolicy(IAggregatePolicy<T, E> aggregatePolicy);

    /**
     * It return the policy for the aggregate context.
     * @return The aggregate policy.
     */
    public IAggregatePolicy<T, E> getPolicy();

    /**
     * It sets the assertion type of the context.
     * @param assertionType The assertion type.
     */
    public void setAssertionType(AssertionType assertionType);

    /**
     * It returns the assertion type of the context..
     * @return The assertion type.
     */
    public AssertionType getAssertionType();
}
