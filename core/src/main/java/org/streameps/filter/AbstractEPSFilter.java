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
package org.streameps.filter;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import org.streameps.filter.listener.IFilteredEventObserver;
import org.streameps.filter.listener.IUnFilteredEventObserver;

/**
 *
 * @author Frank Appiah
 */
public abstract class AbstractEPSFilter<T extends IValueSet> implements IEPSFilter<T> {

    private IFilterValueSet filterValueSet;
    private ArrayDeque<IEPSFilter<T>> filters;
    private IExprEvaluatorContext<T> evaluatorContext;
    private List<IFilteredEventObserver> filterEventObservers;
    private IFilterValueSet unFilteredValueSet;
    private List<IUnFilteredEventObserver> unFilteredEventObservers;

    public AbstractEPSFilter() {
        filterEventObservers = new ArrayList<IFilteredEventObserver>();
        unFilteredEventObservers = new ArrayList<IUnFilteredEventObserver>();
        filters = new ArrayDeque<IEPSFilter<T>>();
    }

    public IEPSFilter<T> nextFilter() {
        return this.filters.poll();
    }

    public void queueFilter(IEPSFilter<T> filter) {
        this.filters.add(filter);
    }

    public ArrayDeque<IEPSFilter<T>> getFilters() {
        return this.filters;
    }

    public IFilterValueSet getFilterValueSet() {
        return this.filterValueSet;
    }

    public IFilterValueSet getUnFilteredValueSet() {
        return unFilteredValueSet;
    }

    public void setUnFilteredValueSet(IFilterValueSet unFilteredValueSet) {
        this.unFilteredValueSet = unFilteredValueSet;
        if (getFilterEventObservers().size() > 0) {
            for (IUnFilteredEventObserver eventObserver : getUnFilterEventObservers()) {
                eventObserver.handleUnFilteredEvent(unFilteredValueSet);
                eventObserver.notifyAllObservers();
            }
        }
    }

    public void setFilterValueSet(IFilterValueSet filterValueSet) {
        this.filterValueSet = filterValueSet;
        if (getUnFilterEventObservers().size() > 0) {
            for (IFilteredEventObserver eventObserver : getFilterEventObservers()) {
                eventObserver.handleFilteredEvent(filterValueSet);
                eventObserver.notifyAllObservers();
            }
        }
    }

    public void setExprEvaluatorContext(IExprEvaluatorContext<T> context) {
        this.evaluatorContext = context;
    }

    public IExprEvaluatorContext<T> getExprEvaluatorContext() {
        return this.evaluatorContext;
    }

    public void setFilterEventObservers(List<IFilteredEventObserver> eventObservers) {
        this.filterEventObservers = eventObservers;
    }

    public List<IFilteredEventObserver> getFilterEventObservers() {
        return this.filterEventObservers;
    }

    public void addFilterEventObserver(IFilteredEventObserver eventObserver) {
        getFilterEventObservers().add(eventObserver);
    }

    public List<IUnFilteredEventObserver> getUnFilterEventObservers() {
        return this.unFilteredEventObservers;
    }

    public void setUnFilterEventObservers(List<IUnFilteredEventObserver> eventObservers) {
        this.unFilteredEventObservers = eventObservers;
    }

    public void addUnFilterEventObserver(IUnFilteredEventObserver eventObserver) {
        getUnFilterEventObservers().add(eventObserver);
    }
    
}
