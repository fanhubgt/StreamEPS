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
package org.streameps.processor;

import java.util.List;
import org.streameps.aggregation.IAggregatePolicy;
import org.streameps.aggregation.IAggregation;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.core.util.SchemaUtil;

/**
 * Implementation of an event stream aggregation. Supported aggregation functions
 * are count, minimum, maximum, mode, etc.
 * Un-implemented functions can be implemented by implementing the IAggregation
 * interface.
 * @see IAggregation
 *
 * @author Frank Appiah
 * @version 0.2.2
 */
public class EventAggregatorPE<T, S> implements IEventAggregator<T, S> {

    private String aggId;
    private IAggregation<T, S> aggregation;
    private SortedAccumulator<S> accumulator;
    private AggregatorListener aggregatorListener = null;
    private IAggregatePolicy aggregatePolicy=null;
    private String propertyName;
    private boolean primitive = false;

    public EventAggregatorPE(String aggId, String propertyName) {
        this.aggId = aggId;
        this.propertyName = propertyName;
        accumulator = new SortedAccumulator<S>();
    }

    public void setAggregatorListener(AggregatorListener aggregatorListener) {
        this.aggregatorListener = aggregatorListener;
    }

    public SortedAccumulator<S> getAccumulator() {
        return accumulator;
    }

    public EventAggregatorPE() {
        accumulator = new SortedAccumulator<S>();
    }

    public void process(Object event) {
        S eventValue = executeInitiator(event);
        executeIterator(eventValue);
    }

    public void output() {
        executeExpirator(null);
    }

    public String getId() {
        return this.aggId;
    }

    public void setAggregatePolicy(IAggregatePolicy aggregatePolicy) {
        this.aggregatePolicy = aggregatePolicy;
    }

    public IAggregatePolicy getAggregatePolicy() {
        return aggregatePolicy;
    }

    public void setAggregation(IAggregation<T, S> aggregation) {
        this.aggregation = aggregation;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public S executeInitiator(Object event) {
        Class<?> clazz = event.getClass();

        if (clazz.isPrimitive() || event instanceof String) {
            return (S) event;
        }
        if(aggregatePolicy!=null)aggregatePolicy.executeInitiator(event);
        return (S) SchemaUtil.getPropertyValue(event, propertyName);
    }

    public void executeIterator(S event) {
        accumulator.processAt(event.getClass().getName(), event);
        if(aggregatePolicy!=null)aggregatePolicy.executeIterator(event);
    }

    public void executeExpirator(S event) {
        List<S> list = (List<S>) accumulator.getMap().firstEntry().getValue();
        for (S evt : list) {
            aggregation.process(aggregation.getBuffer(), evt);
        }
        if (aggregatorListener != null) {
            aggregatorListener.onAggregate(aggregation);
        }
        accumulator.clear();
        if(aggregatePolicy!=null)aggregatePolicy.executeExpirator(event);
    }

    public IAggregation<T, S> getAggregator() {
        return this.aggregation;
    }

}
