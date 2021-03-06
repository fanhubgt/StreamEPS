/*
 * ====================================================================
 *  StreamEPS Platform
 * 
 *  Copyright 2011.
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
package org.streameps.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import org.streameps.filter.listener.IFilteredEventObserver;
import org.streameps.filter.listener.IUnFilteredEventObserver;

/**
 *
 * @author Frank Appiah
 */
public class FilterManager implements IFilterManager<FilterValueSet> {

    private PriorityQueue<IEPSFilter<FilterValueSet>> priorityQueue;
    private RangeFilter rangeFilter;
    private ComparisonFilter comparisonFilter;
    private InNotValueFilter inNotValueFilter;
    private IFilterValueSet filterValueSet;
    private List<IFilteredEventObserver> filteredEventObservers;
    private List<IUnFilteredEventObserver> unFilteredEventObservers;

    public FilterManager() {
        priorityQueue = new PriorityQueue<IEPSFilter<FilterValueSet>>();
        filteredEventObservers = new ArrayList<IFilteredEventObserver>();
        unFilteredEventObservers = new ArrayList<IUnFilteredEventObserver>();
    }

    public FilterManager(PriorityQueue<IEPSFilter<FilterValueSet>> priorityQueue) {
        this.priorityQueue = priorityQueue;
        filteredEventObservers = new ArrayList<IFilteredEventObserver>();
        unFilteredEventObservers = new ArrayList<IUnFilteredEventObserver>();
    }

    public void queueFilter(IEPSFilter<FilterValueSet> filter) {
        this.priorityQueue.offer(filter);
    }

    public void processFilter(IEPSFilter<FilterValueSet> ePSFilter) {
        ePSFilter.setFilterEventObservers(getFilterEventObservers());
        ePSFilter.setUnFilterEventObservers(getUnFilterEventObservers());

        switch (ePSFilter.getFilterType()) {
            case COMPARISON:
                comparisonFilter = (ComparisonFilter) ePSFilter;
                comparisonFilter.filter(ePSFilter.getExprEvaluatorContext());
                setFilterValueSet(comparisonFilter.getFilterValueSet());
                break;
            case IN_NOT_VALUES:
                inNotValueFilter = (InNotValueFilter) ePSFilter;
                inNotValueFilter.filter(ePSFilter.getExprEvaluatorContext());
                setFilterValueSet(inNotValueFilter.getFilterValueSet());
                break;
            case RANGE:
                rangeFilter = (RangeFilter) ePSFilter;
                rangeFilter.filter(ePSFilter.getExprEvaluatorContext());
                setFilterValueSet(rangeFilter.getFilterValueSet());
                break;
            default:
                ePSFilter.filter(ePSFilter.getExprEvaluatorContext());
        }
       
    }

    public FilterValueSet getFilterValueSet() {
        return (FilterValueSet) this.filterValueSet;
    }

    public void setFilterValueSet(IFilterValueSet filterValueSet) {
        this.filterValueSet = filterValueSet;
    }

    public List<IFilteredEventObserver> getFilterEventObservers() {
        return this.filteredEventObservers;
    }

    public List<IUnFilteredEventObserver> getUnFilterEventObservers() {
        return this.unFilteredEventObservers;
    }
    
}
