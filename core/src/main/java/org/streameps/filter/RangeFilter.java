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

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.context.IContextEntry;
import org.streameps.context.IPartitionWindow;
import org.streameps.context.IPredicateTerm;
import org.streameps.filter.eval.range.IRangeTerm;
import org.streameps.filter.eval.range.RangeTerm;

/**
 * The range filter container for the filter manager.
 * 
 * @author Frank Appiah
 */
public class RangeFilter<T extends IRangeValueSet<ISortedAccumulator>>
        extends AbstractEPSFilter<IRangeValueSet<ISortedAccumulator>>
        implements IEPSFilter<IRangeValueSet<ISortedAccumulator>> {

    private IRangeFilterExprn rangeFilterExprn;
    private List<IRangeTerm> rangeTerms;

    public RangeFilter() {
        super();
    }

    public RangeFilter(IRangeFilterExprn rangeFilterExprn, List<IRangeTerm> rangeTerms) {
        this.rangeFilterExprn = rangeFilterExprn;
        this.rangeTerms = rangeTerms;
    }

    public void filter(IExprEvaluatorContext<IRangeValueSet<ISortedAccumulator>> context) throws EvaluatorContextException {
        super.setExprEvaluatorContext(context);

        IFilterValueSet<ISortedAccumulator> unFilteredValueSet = new FilterValueSet<ISortedAccumulator>();
        IFilterValueSet<ISortedAccumulator> filterValueSet = new FilterValueSet<ISortedAccumulator>();
        IRangeValueSet<ISortedAccumulator> valueSet = context.getEventContainer();
        IPartitionWindow<ISortedAccumulator> window = valueSet.getValueSet();
        ISortedAccumulator accumulator = window.getWindow();

        IContextEntry contextEntry = context.getContextEntry();
        rangeFilterExprn = (IRangeFilterExprn) contextEntry.getPredicateExpr();
        setRangeFilterExprn(rangeFilterExprn);
        List<IPredicateTerm> term = contextEntry.getPredicateTerms();

        rangeTerms = buildRangeTerm(term, valueSet);
        setRangeTerm(rangeTerms);

        TreeMap<Object, List<?>> treeMap = accumulator.getMap();

        List<?> eventObjects = treeMap.firstEntry().getValue();
        for (Object eventObject : eventObjects) {
            if (rangeFilterExprn.evalRange(eventObject, rangeTerms)) {
                filterValueSet.getValueSet().getWindow().processAt(eventObject.getClass().getName(), eventObject);
            } else {
                unFilteredValueSet.getValueSet().getWindow().processAt(eventObject.getClass().getName(), eventObject);
            }
        }
        super.setFilterValueSet(filterValueSet);
        super.setUnFilteredValueSet(unFilteredValueSet);
    }

    private List<IRangeTerm> buildRangeTerm(List<IPredicateTerm> predicateTerms, IRangeValueSet<ISortedAccumulator> valueSet) {
        IRangeTerm rangeTerm;
        List<IRangeTerm> rangeTms = new ArrayList<IRangeTerm>();
        for (IPredicateTerm ipt : predicateTerms) {
            rangeTerm = new RangeTerm(ipt.getPropertyName(), valueSet);
            rangeTms.add(rangeTerm);
        }
        return rangeTms;
    }

    public FilterType getFilterType() {
        return FilterType.RANGE;
    }

    public void setRangeFilterExprn(IRangeFilterExprn rangeFilterExprn) {
        this.rangeFilterExprn = rangeFilterExprn;
    }

    public void setRangeTerm(List<IRangeTerm> rangeTerms) {
        this.rangeTerms = rangeTerms;
    }
}
