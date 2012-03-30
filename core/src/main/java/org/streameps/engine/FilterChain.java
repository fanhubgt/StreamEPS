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
package org.streameps.engine;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.streameps.aggregation.collection.Accumulator;
import org.streameps.filter.FilterType;
import org.streameps.filter.IFilterValueSet;

/**
 *
 * @author Frank Appiah
 */
public class FilterChain<T extends Accumulator> implements IFilterChain<T> {

    private IFilterContext<T> filterContext;
    private List<IFilterContext<T>> filterContexts;
    private Map<FilterType, IFilterVisitor<T>> filterVisitorMap;
    private Map<FilterType, IFilterValueSet<T>> filterValueMap;

    public FilterChain() {
        filterContexts = new ArrayList<IFilterContext<T>>();
        filterVisitorMap = new EnumMap<FilterType, IFilterVisitor<T>>(FilterType.class);
        filterValueMap = new EnumMap<FilterType, IFilterValueSet<T>>(FilterType.class);
    }

    public FilterChain(IFilterContext<T> filterContext, List<IFilterContext<T>> filterContexts, Map<FilterType, IFilterVisitor<T>> filterVisitorMap) {
        this.filterContext = filterContext;
        this.filterContexts = filterContexts;
        this.filterVisitorMap = filterVisitorMap;
    }

    public void addFilterContext(IFilterContext<T> context) {
        getFilterContexts().add(context);
        filterContext = context;
    }

    public List<IFilterContext<T>> getFilterContexts() {
        return this.filterContexts;
    }

    public void setFilterContexts(List<IFilterContext<T>> filterContexts) {
        this.filterContexts = filterContexts;
    }

    public void executeVisitor() {
        for (IFilterContext<T> context : filterContexts) {
            FilterType cntfilterType = context.getEPSFilter().getFilterType();
            IFilterValueSet filterValueSet;
            IFilterVisitor<T> filterVisitor=null;
            switch (cntfilterType) {
                case COMPARISON:
                    filterValueSet = filterVisitor.visitContext(filterContext);
                    getFilterValueMap().put(cntfilterType, filterValueSet);
                    break;
                case IN_NOT_VALUES:
                    filterValueSet = filterVisitor.visitContext(filterContext);
                    getFilterValueMap().put(cntfilterType, filterValueSet);
                    break;
                case RANGE:
                    filterValueSet = filterVisitor.visitContext(filterContext);
                    getFilterValueMap().put(cntfilterType, filterValueSet);
                    break;
                case NULL:
                    filterValueSet = filterVisitor.visitContext(filterContext);
                    getFilterValueMap().put(cntfilterType, filterValueSet);
                    break;
                default:
                    throw new IllegalArgumentException("Filter type is not supported.");
            }
        }
    }

    public Map<FilterType, IFilterValueSet<T>> getFilterValueMap() {
        return this.filterValueMap;
    }

    public void setFilterValueMap(Map<FilterType, IFilterValueSet<T>> valueMap) {
        this.filterValueMap = valueMap;
    }

    public void setFilterVisitorMap(Map<FilterType, IFilterVisitor<T>> visitorMap) {
        this.filterVisitorMap = visitorMap;
    }

    public Map<FilterType, IFilterVisitor<T>> getFilterVisitorMap() {
        return this.filterVisitorMap;
    }

    public void addFilterVisitor(FilterType filterType, IFilterVisitor<T> filterVisitor) {
        getFilterVisitorMap().put(filterType, filterVisitor);
    }

    public void addFilterValueSet(FilterType filterType, IFilterValueSet<T> filterValueSet) {
        getFilterValueMap().put(filterType, filterValueSet);
    }
}
