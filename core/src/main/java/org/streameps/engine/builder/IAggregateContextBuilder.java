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

package org.streameps.engine.builder;

import java.util.List;
import org.streameps.aggregation.IAggregatePolicy;
import org.streameps.aggregation.IAggregation;
import org.streameps.engine.IAggregateContext;
import org.streameps.operator.assertion.AssertionType;
import org.streameps.processor.AggregatorListener;

/**
 *
 * @author  Frank Appiah
 */
public interface IAggregateContextBuilder {

    /**
     * It builds the aggregate context.
     * @param aggregateProperty The aggregate property to be set.
     * @param aggregation The aggregation evaluator.
     * @param threshold The threshold value;
     * @param assertionType The assertion type for the context.
     * @return An aggregate context.
     */
    AggregateContextBuilder buildDeciderAggregateContext(String aggregateProperty, IAggregation aggregation, Object threshold, AssertionType assertionType);

    /**
     * It builds the aggregate context for the decider aggregation detection process
     * or the EPSProducer to produce an aggregate.
     * @param aggregateProperty The aggregate property to be set.
     * @param aggregation The aggregation evaluator.
     * @param threshold The threshold value;
     * @param assertionType The assertion type for the context.
     * @param policy The aggregation policy to be set.
     * @return An aggregate context.
     */
    AggregateContextBuilder buildDeciderAggregateContext(String aggregateProperty, IAggregation aggregation, Object threshold, AssertionType assertionType, IAggregatePolicy policy);

    /**
     * It builds the aggregate context.
     * @param aggregateProperty The aggregate property to be set.
     * @param aggregation The aggregation evaluator.
     * @param assertionType The assertion type for the context.
     * @return An aggregate context.
     */
    AggregateContextBuilder buildProducerAggregateContext(String aggregateProperty, IAggregation aggregation);

    /**
     * It builds the aggregate context for the EPSProducer aggregation process.
     *
     * @param aggregateProperty The aggregate property to be set.
     * @param aggregation The aggregation evaluator.
     * @param threshold The threshold value for the detection.
     * @param assertionType The assertion type for the context.
     * @param policy The aggregation policy to be set.
     * @return An aggregate context.
     */
    AggregateContextBuilder buildProducerAggregateContext(String aggregateProperty, IAggregation aggregation, IAggregatePolicy policy);

    IAggregateContext getAggregateContext();

    void setAggregateContext(IAggregateContext aggregateContext);

    AggregatorListener getAggregatorListener();

    void setAggregatorListener(AggregatorListener aggregatorListener);

    List<IAggregateContext> getAggregateContexts();

    void setAggregateContexts(List<IAggregateContext> aggregateContexts);

}
