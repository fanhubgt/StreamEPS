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

import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.context.IContextEntry;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.IPredicateTerm;
import org.streameps.core.util.IDUtil;

/**
 *
 * @author Frank Appiah
 */
public class ComparisonFilter<T extends IComparisonValueSet<SortedAccumulator>>
        extends AbstractEPSFilter<IComparisonValueSet<SortedAccumulator>>
        implements IEPSFilter<IComparisonValueSet<SortedAccumulator>> {

    private IEventContentFilterExprn contentFilterExpr;
    private IPredicateTerm predicateTerm;

    public ComparisonFilter() {
        super();
    }

    
    public void filter(IExprEvaluatorContext<IComparisonValueSet<SortedAccumulator>> context) {
        IFilterValueSet<SortedAccumulator> resultValueSet = new FilterValueSet<SortedAccumulator>
                (IDUtil.getUniqueID(new Date().toString()));
        setExprEvaluatorContext(context);
        IComparisonValueSet<SortedAccumulator> valueSet = context.getEventContainer();
        IPartitionWindow<SortedAccumulator> window = valueSet.getValueSet();
        SortedAccumulator accumulator = window.getWindow();
        IContextEntry contextEntry = context.getContextEntry();
        TreeMap<Object, List<Object>> treeMap = accumulator.getMap();

        List<Object> objects = treeMap.firstEntry().getValue();
        switch (contentFilterExpr.getContentEvalType()) {
            case STRING:
            case COMPARISON:
                computeFilter(objects, resultValueSet);
                setFilterValueSet(resultValueSet);
                break;
            default:
                setFilterValueSet(null);
        }
        //todo: assess if the comparison filter needs to nested.
    }

    private void computeFilter(List<Object> filterObjects, IFilterValueSet<SortedAccumulator> filterValueSet) {
        for (Object filterObject : filterObjects) {
            boolean filterBool = contentFilterExpr.evalExpr(filterObject, predicateTerm);
            if (filterBool) {
                filterValueSet.getValueSet().getWindow().processAt(filterObject.getClass().getName(), filterObject);
            }
        }
    }

    public void setContentFilterExpr(IEventContentFilterExprn contentFilterExpr) {
        this.contentFilterExpr = contentFilterExpr;
    }

    public void setPredicateTerm(IPredicateTerm predicateTerm) {
        this.predicateTerm = predicateTerm;
    }

    public IPredicateTerm getPredicateTerm() {
        return predicateTerm;
    }

    public FilterType getFilterType() {
        return FilterType.COMPARISON;
    }
}
