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

import org.streameps.aggregation.IAggregatePolicy;
import org.streameps.aggregation.IAggregation;
import org.streameps.operator.assertion.AssertionType;

/**
 *
 * @author Frank Appiah
 */
public class AggregateContext<T, E> implements IAggregateContext<T, E> {

    private String aggregateProperty;
    private IAggregation<T, E> aggregation;
    private E threshold;
    private E aggregateResult;
    private AssertionType assertionType;
    private IAggregatePolicy<T, E> aggregatePolicy;

    public AggregateContext() {
    }

    public AggregateContext(String aggregateProperty,
            IAggregation<T, E> aggregation,
            E threshold,
            AssertionType assertionType) {
        this.aggregateProperty = aggregateProperty;
        this.aggregation = aggregation;
        this.threshold = threshold;
        this.assertionType = assertionType;
    }

    public void setAggregateProperty(String property) {
        this.aggregateProperty = property;
    }

    public String getAggregateProperty() {
        return this.aggregateProperty;
    }

    public void setAggregator(IAggregation<T, E> aggregation) {
        this.aggregation = aggregation;
    }

    public IAggregation<T, E> getAggregator() {
        return this.aggregation;
    }

    public void setThresholdValue(E threshold) {
        this.threshold = threshold;
    }

    public E getThresholdValue() {
        return this.threshold;
    }

    public E getAggregateResult() {
        return this.aggregateResult;
    }

    public void setAggregateResult(E result) {
        this.aggregateResult = result;
    }

    public void setPolicy(IAggregatePolicy<T, E> aggregatePolicy) {
        this.aggregatePolicy = aggregatePolicy;
    }

    public IAggregatePolicy<T, E> getPolicy() {
        return this.aggregatePolicy;
    }

    public void setAssertionType(AssertionType assertionType) {
        this.assertionType = assertionType;
    }

    public AssertionType getAssertionType() {
        return this.assertionType;
    }
}
