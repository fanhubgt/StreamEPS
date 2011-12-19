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
package org.streameps.engine.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.streameps.context.ContextEntry;
import org.streameps.context.IContextEntry;
import org.streameps.context.IPredicateExpr;
import org.streameps.context.IPredicateTerm;
import org.streameps.context.PredicateOperator;
import org.streameps.context.PredicateTerm;
import org.streameps.util.IDUtil;
import org.streameps.engine.FilterContext;
import org.streameps.engine.IFilterContext;
import org.streameps.filter.ComparisonFilter;
import org.streameps.filter.ExprEvaluatorContext;
import org.streameps.filter.FilterOperator;
import org.streameps.filter.FilterType;
import org.streameps.filter.IEPSFilter;
import org.streameps.filter.IExprEvaluatorContext;
import org.streameps.filter.IFilterValueSet;
import org.streameps.filter.InNotValueFilter;
import org.streameps.filter.RangeFilter;
import org.streameps.filter.listener.IFilteredEventObserver;
import org.streameps.filter.listener.IUnFilteredEventObserver;

/**
 *
 * @author Frank Appiah
 */
public class FilterContextBuilder {

    private IFilterContext filterContext = null;
    private String identifier = null;
    private IEPSFilter filter;
    private IExprEvaluatorContext evaluatorContext;
    private IContextEntry contextEntry = null;
    private FilterType filterType;
    private FilterOperator filterOperator;
    private IPredicateExpr predicateExpr;
    private List<IPredicateTerm> predicateTerms;
    private String eventType;

    public FilterContextBuilder() {
        filterContext = new FilterContext();
        predicateTerms = new ArrayList<IPredicateTerm>();
    }

    public FilterContextBuilder(IFilterContext fordwarderContext) {
        this.filterContext = fordwarderContext;
        predicateTerms = new ArrayList<IPredicateTerm>();
    }

    public FilterContextBuilder(String identifier, FilterType filterType, FilterOperator filterOperator) {
        this.identifier = identifier;
        this.filterType = filterType;
        this.filterOperator = filterOperator;
        predicateTerms = new ArrayList<IPredicateTerm>();
    }

    public FilterContextBuilder buildEvaluatorContext(FilterType filterType, FilterOperator filterOperator, IPredicateExpr predicateExpr, IFilterValueSet filterValueSet) {
        this.filterType = filterType;
        this.filterOperator = filterOperator;
        this.predicateExpr = predicateExpr;

        buildContextEntry(predicateExpr);

        evaluatorContext = new ExprEvaluatorContext(this.filterType, this.filterOperator);
        evaluatorContext.setContextEntry(contextEntry);
        evaluatorContext.setEventContainer(filterValueSet);

        return this;
    }

    public FilterContextBuilder buildEvaluatorContext(String eventType, FilterType filterType, FilterOperator filterOperator, IPredicateExpr predicateExpr, IFilterValueSet filterValueSet) {
        this.filterType = filterType;
        this.filterOperator = filterOperator;
        this.eventType = eventType;

        buildContextEntry(eventType, predicateExpr);

        evaluatorContext = new ExprEvaluatorContext(this.filterType, this.filterOperator);
        evaluatorContext.setContextEntry(contextEntry);
        evaluatorContext.setEventContainer(filterValueSet);

        return this;
    }

    public FilterContextBuilder buildEvaluatorContext(String eventType, FilterType filterType, FilterOperator filterOperator, IPredicateExpr predicateExpr) {
        this.filterType = filterType;
        this.filterOperator = filterOperator;
        this.eventType = eventType;

        buildContextEntry(eventType, predicateExpr);

        evaluatorContext = new ExprEvaluatorContext(this.filterType, this.filterOperator);
        evaluatorContext.setContextEntry(contextEntry);

        return this;
    }

    public FilterContextBuilder buildEvaluatorContext(FilterType filterType, FilterOperator filterOperator, IPredicateExpr predicateExpr) {
        this.filterType = filterType;
        this.filterOperator = filterOperator;

        buildContextEntry(predicateExpr);

        evaluatorContext = new ExprEvaluatorContext(this.filterType, this.filterOperator);
        evaluatorContext.setContextEntry(contextEntry);

        return this;
    }

    public FilterContextBuilder buildEvaluatorContext(FilterType filterType, FilterOperator filterOperator) {
        this.filterType = filterType;
        this.filterOperator = filterOperator;

        evaluatorContext = new ExprEvaluatorContext(this.filterType, this.filterOperator);
        evaluatorContext.setContextEntry(contextEntry);

        return this;
    }

    public FilterContextBuilder buildEvaluatorContext(FilterType filterType) {
        this.filterType = filterType;
        evaluatorContext = new ExprEvaluatorContext(this.filterType);
        evaluatorContext.setContextEntry(contextEntry);

        return this;
    }

    public FilterContextBuilder buildFilterContext(IFilterValueSet filterValueSet) {
        filterContext = new FilterContext();
        identifier = (identifier == null) ? IDUtil.getUniqueIDRand() : identifier;
        evaluatorContext.setEventContainer(filterValueSet);

        filterContext.setIdentifier(identifier);
        filterContext.setEPSFilter(filter);
        filterContext.setEvaluatorContext(evaluatorContext);
        filterContext.setFilteredValue(filterValueSet);
        return this;
    }

    public FilterContextBuilder buildFilterContext() {
        filterContext = new FilterContext();
        identifier = (identifier == null) ? IDUtil.getUniqueID(new Date().toString()) : identifier;
        evaluatorContext.setEventContainer(null);

        filterContext.setIdentifier(identifier);
        filterContext.setEPSFilter(filter);
        filterContext.setEvaluatorContext(evaluatorContext);
        return this;
    }

    public FilterContextBuilder buildContextEntry(String eventType, IPredicateExpr predicateExpr) {
        contextEntry = new ContextEntry();
        contextEntry.setEventType(eventType);
        contextEntry.setPredicateExpr(predicateExpr);
        contextEntry.setPredicateTerms(predicateTerms);
        this.eventType = eventType;
        return this;
    }

    public FilterContextBuilder buildContextEntry(IPredicateExpr predicateExpr) {
        contextEntry = new ContextEntry();
        contextEntry.setEventType(eventType);
        contextEntry.setPredicateExpr(predicateExpr);
        contextEntry.setPredicateTerms(predicateTerms);

        this.evaluatorContext.setContextEntry(contextEntry);
        return this;
    }

    public FilterContextBuilder buildPredicateTerm(String propertyName, PredicateOperator operator, Object propertyValue) {
        IPredicateTerm predicateTerm = new PredicateTerm(propertyName, operator, propertyValue);
        predicateTerms.add(predicateTerm);

        return this;
    }
     public FilterContextBuilder buildPredicateTerm(String propertyName, Object propertyValue) {
        IPredicateTerm predicateTerm = new PredicateTerm(propertyName, propertyValue);
        predicateTerms.add(predicateTerm);

        return this;
    }

    public FilterContextBuilder buildEPSFilter(IFilterValueSet filterValueSet) {
        switch (filterType) {
            case COMPARISON:
                filter = new ComparisonFilter();
                break;
            case IN_NOT_VALUES:
                filter = new InNotValueFilter();
                break;
            case RANGE:
                filter = new RangeFilter();
        }
        filter.setExprEvaluatorContext(evaluatorContext);
        filter.setFilterValueSet(filterValueSet);

        return this;
    }

    public FilterContextBuilder buildEPSFilter() {
        switch (filterType) {
            case COMPARISON:
                filter = new ComparisonFilter();
                break;
            case IN_NOT_VALUES:
                filter = new InNotValueFilter();
                break;
            case RANGE:
                filter = new RangeFilter();
        }
        filter.setExprEvaluatorContext(evaluatorContext);

        return this;
    }

    public FilterContextBuilder buildEPSFilter(IFilterValueSet filterValueSet, FilterType filterType) {
        this.filterType = filterType;
        switch (filterType) {
            case COMPARISON:
                filter = new ComparisonFilter();
                break;
            case IN_NOT_VALUES:
                filter = new InNotValueFilter();
                break;
            case RANGE:
                filter = new RangeFilter();
        }
        filter.setExprEvaluatorContext(evaluatorContext);
        filter.setFilterValueSet(filterValueSet);

        return this;
    }

    public FilterContextBuilder buildFilterListeners(IFilteredEventObserver filteredEventObserver, IUnFilteredEventObserver unFilteredEventObserver) {
        filter.getFilterEventObservers().add(filteredEventObserver);
        filter.getUnFilterEventObservers().add(unFilteredEventObserver);
        return this;
    }

    public FilterContextBuilder buildFilterListener(IFilteredEventObserver filteredEventObserver) {
        filter.getFilterEventObservers().add(filteredEventObserver);
        return this;
    }

    public FilterContextBuilder buildFilterListener(IUnFilteredEventObserver unFilteredEventObserver) {
        filter.getUnFilterEventObservers().add(unFilteredEventObserver);
        return this;
    }

    public IExprEvaluatorContext getEvaluatorContext() {
        return evaluatorContext;
    }

    public IEPSFilter getFilter() {
        return filter;
    }

    public IFilterContext getFilterContext() {
        return filterContext;
    }
}
