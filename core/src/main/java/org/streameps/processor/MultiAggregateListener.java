/*
 * ====================================================================
 *  SoftGene Technologies
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
package org.streameps.processor;

import java.util.ArrayList;
import java.util.List;
import org.streameps.aggregation.IAggregation;
import org.streameps.engine.IAggregateContext;

/**
 *
 * @author Frank Appiah
 */
public class MultiAggregateListener implements AggregatorListener<IAggregation> {

    private List<AggregatorListener> aggregatorListeners;
    private IAggregateContext aggregateContext;
    private IAggregation aggregate;
    private String identifier;

    public MultiAggregateListener() {
        aggregatorListeners = new ArrayList<AggregatorListener>();
    }

    public void onAggregate(IAggregation aggregate) {
        this.aggregate = aggregate;
        for (AggregatorListener listener : getAggregatorListeners()) {
            listener.onAggregate(aggregate);
        }
    }

    public IAggregation getAggregate() {
        return aggregate;
    }

    public void setAggregateContext(IAggregateContext aggregateContext) {
        this.aggregateContext = aggregateContext;
        for (AggregatorListener listener : getAggregatorListeners()) {
            listener.setAggregateContext(aggregateContext);
        }
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier=identifier;
    }

    public void addAggregateListener(AggregatorListener listener) {
        getAggregatorListeners().add(listener);
    }

    public void removeAggregateListener(AggregatorListener listener) {
        getAggregatorListeners().remove(listener);
    }

    public void setAggregatorListeners(List<AggregatorListener> aggregatorListeners) {
        this.aggregatorListeners = aggregatorListeners;
    }

    public IAggregateContext getAggregateContext() {
        return aggregateContext;
    }

    public List<AggregatorListener> getAggregatorListeners() {
        return aggregatorListeners;
    }

    public AggregatePoint getAggregatePoint() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
