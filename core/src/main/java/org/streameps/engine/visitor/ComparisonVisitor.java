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
package org.streameps.engine.visitor;

import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.engine.IFilterContext;
import org.streameps.engine.IFilterVisitor;
import org.streameps.filter.ComparisonValueSet;
import org.streameps.filter.FilterType;
import org.streameps.filter.IComparisonValueSet;
import org.streameps.filter.IEPSFilter;
import org.streameps.filter.IFilterValueSet;

/**
 *
 * @author Frank Appiah
 */
public class ComparisonVisitor implements IFilterVisitor<ISortedAccumulator> {

    private IFilterContext<ISortedAccumulator> filterContext;
    private IFilterValueSet<ISortedAccumulator> filterValueSet;
    private IEPSFilter filter;

    public IFilterValueSet<ISortedAccumulator> visitContext(IFilterContext filterContext) {
        FilterType type = filterContext.getEPSFilter().getFilterType();
        if (type == FilterType.COMPARISON) {
            this.filterContext = filterContext;
            filter = filterContext.getEPSFilter();
            filter.filter(filterContext.getEvaluatorContext());
            filterValueSet = filter.getFilterValueSet();
            if (filter.getFilters() != null && filter.getFilters().size() > 0) {
                for (Object childFilter : filter.getFilters()) {
                    IEPSFilter ePSFilter = (IEPSFilter) childFilter;
                    type = ePSFilter.getFilterType();
                    if (type == FilterType.COMPARISON) {
                        String identifier = ((IComparisonValueSet<ISortedAccumulator>) filter.getExprEvaluatorContext()).getValueIdentifier();
                        IComparisonValueSet<ISortedAccumulator> valueSet = new ComparisonValueSet<ISortedAccumulator>(filterValueSet.getValueSet(), identifier);
                        ePSFilter.getExprEvaluatorContext().setEventContainer(valueSet);
                        ePSFilter.filter(ePSFilter.getExprEvaluatorContext());
                        filterValueSet = ePSFilter.getFilterValueSet();
                    }
                }
            }
        }
        return filterValueSet;
    }

    public IFilterValueSet<ISortedAccumulator> getFilterValueSet() {
        return filterValueSet;
    }

    public IFilterContext<ISortedAccumulator> getFilterContext() {
        return filterContext;
    }
}
