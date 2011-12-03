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
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.aggregation.collection.SortedAccumulator;
import org.streameps.core.IMatchedEventSet;
import org.streameps.core.util.IDUtil;

/**
 *
 * @author Frank Appiah
 */
public class FilterValueSetWrapper implements IFilterValueSetWrapper {

    private ISortedAccumulator accumulator = null;
    private IMatchedEventSet eventSet = null;
    private FilterType filterType;

    public FilterValueSetWrapper() {
        accumulator=new SortedAccumulator();
    }

    public FilterValueSetWrapper(IMatchedEventSet eventSet, FilterType filterType) {
        this.eventSet = eventSet;
        this.filterType = filterType;
        accumulator=new SortedAccumulator();
    }

    public FilterValueSetWrapper(ISortedAccumulator accumulator) {
        this.accumulator = accumulator;
    }

    public FilterValueSetWrapper(ISortedAccumulator accumulator, FilterType filterType) {
        this.accumulator = accumulator;
        this.filterType = filterType;
    }

    public IComparisonValueSet<ISortedAccumulator> getComparisonValueSet() {
        IComparisonValueSet<ISortedAccumulator> valueSet = new ComparisonValueSet<ISortedAccumulator>();
        valueSet.setValueIdentifier(IDUtil.getUniqueID(new Date().toString()));
        accumulator=new SortedAccumulator();
        for (Object event : this.eventSet) {
            accumulator.processAt(event.getClass().getName(), event);
        }
        valueSet.getValueSet().setWindow(accumulator);
        return valueSet;
    }

    public IRangeValueSet<ISortedAccumulator> getRangeValueSet() {
        IRangeValueSet<ISortedAccumulator> valueSet = new RangeValueSet<ISortedAccumulator>
                (IDUtil.getUniqueID(new Date().toString()));
        valueSet.setValueIdentifier(IDUtil.getUniqueID(new Date().toString()));
        if (accumulator.getSizeCount()<0) {
            populateSortedAccumulator(eventSet);
        }
        valueSet.getValueSet().setWindow(accumulator);
        return valueSet;
    }

    public IInNotValueSet<ISortedAccumulator> getInNotValueSet() {
        IInNotValueSet<ISortedAccumulator> valueSet = new InNotValueSet<ISortedAccumulator>();
        valueSet.setValueIdentifier(IDUtil.getUniqueID(new Date().toString()));
        if (accumulator.getSizeCount()<0) {
            populateSortedAccumulator(eventSet);
        }
        valueSet.getValueSet().setWindow(accumulator);
        return valueSet;
    }

    private void populateSortedAccumulator(IMatchedEventSet eventSet) {
        for (Object event : eventSet) {
            accumulator.processAt(event.getClass().getName(), event);
        }
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }
    
}
