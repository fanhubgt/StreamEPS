/*
 * ====================================================================
 *  StreamEPS Platform
 *
 * (C) Copyright 2011
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
package org.streameps.filter;

import java.util.ArrayDeque;
import java.util.List;
import org.streameps.filter.listener.FilterEventObserver;

/**
 * It filters the event instances from the channel input stream.
 * 
 * @author Frank Appiah
 */
public interface IEPSFilter<T extends IValueSet>{

    /**
     * It performs the filtering operation before receiving event instances.
     * @param event An event instance received
     * @return An event instance that matched the filter expression.
     */
    public void filter(IExprEvaluatorContext<T> context);

    /**
     * It returns the expression evaluator context.
     * @param context The evaluator context.
     */
    public void setExprEvaluatorContext(IExprEvaluatorContext<T> context);

    /**
     * It returns the expression evaluator context.
     * @return The evaluator context.
     */
    public IExprEvaluatorContext<T> getExprEvaluatorContext();

    /**
     * It returns the next filter in chain to be executed.
     * @return An instance of a filter.
     */
    public IEPSFilter<T> nextFilter();

    /**
     * It queues the filter in a priority queue.
     * @param filter Filter to add to queue.
     */
    public void queueFilter(IEPSFilter<T> filter);

    /**
     * It returns the filters in the queue in filter set.
     * @return an array of filters.
     */
    public ArrayDeque<IEPSFilter<T>> getFilters();

    /**
     * It returns the filter value set.
     * @return The filter value set.
     */
    public IFilterValueSet getFilterValueSet();

    /**
     * It sets the filter value set for the filter process.
     * @param filterValueSet  The filter value set.
     */
    public void setFilterValueSet(IFilterValueSet filterValueSet);

    /**
     * It returns the filter type.
     * @return The filter type
     */
    public FilterType getFilterType();

    /**
     * It sets the filter event observers.
     * @param eventObservers The instance of filter event observers.
     */
    public void setFilterEventObservers(List<FilterEventObserver> eventObservers);

    /**
     * It returns the filter event observers.
     * @return  The instance of filter event observers.
     */
    public List<FilterEventObserver> getFilterEventObservers();
   
}
