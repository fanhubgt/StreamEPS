/*
 * ====================================================================
 *  StreamEPS Platform
 *  (C) Copyright 2011
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

import org.streameps.context.IContextEntry;

/**
 *
 * @author Frank Appiah
 */
public class ExprEvaluatorContext<T extends IValueSet> implements IExprEvaluatorContext<T> {

    private FilterType filterType;
    private FilterOperator operator;
    private IContextEntry contextEntry;
    private transient T eventContainer;
    private String identifier;

    public ExprEvaluatorContext() {
    }

    public ExprEvaluatorContext(FilterType filterType) {
        this.filterType = filterType;
    }

    public ExprEvaluatorContext(FilterType filterType, FilterOperator operator) {
        this.filterType = filterType;
        this.operator = operator;
    }

    public ExprEvaluatorContext(FilterType filterType, FilterOperator operator, IContextEntry contextEntry, T eventContainer) {
        this.filterType = filterType;
        this.operator = operator;
        this.contextEntry = contextEntry;
        this.eventContainer = eventContainer;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }

    public FilterOperator getOperator() {
        return operator;
    }

    public void setOperator(FilterOperator operator) {
        this.operator = operator;
    }

    public T getEventContainer() {
        return eventContainer;
    }

    public void setEventContainer(T eventContainer) {
        this.eventContainer = eventContainer;
    }

    public void setContextEntry(IContextEntry contextEntry) {
        this.contextEntry = contextEntry;
    }

    public IContextEntry getContextEntry() {
        return this.contextEntry;
    }

    public void setIdentifier(String identifier) {
        this.identifier=identifier;
    }

    public String getIdentifier() {
        return this.identifier;
    }
}
