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
package org.streameps.filter.eval.range;

import java.util.List;
import org.streameps.aggregation.MaxAggregation;
import org.streameps.aggregation.MinAggregation;
import org.streameps.aggregation.ModeAggregation;
import org.streameps.aggregation.collection.HashMapCounter;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.core.util.SchemaUtil;
import org.streameps.filter.FilterOperator;
import org.streameps.filter.IRangeFilterExprn;
import org.streameps.filter.IRangeValueSet;

/**
 *
 * @author Frank Appiah
 */
public class InRangeValueCountEval<R> implements IRangeFilterExprn<R> {

    private MaxAggregation maxAggregation;
    private MinAggregation minAggregation;
    private ModeAggregation modeAggregation;
    /**
     * A +/- marginal value.
     */
    private double marginalValue = 0.0;

    public InRangeValueCountEval() {
        maxAggregation = new MaxAggregation();
        minAggregation = new MinAggregation();
        modeAggregation = new ModeAggregation();
    }

    public FilterOperator getFilterOperator() {
        return FilterOperator.IN_LIST_OF_VALUES;
    }

    public boolean evalRange(R eventInstance, IRangeTerm rangeTerm) {
        Double eventValue = (Double) SchemaUtil.getPropertyValue(eventInstance, rangeTerm.getPropertyName());

        IRangeEndPoint<Double> rangeEndPoint = rangeTerm.getRangeEndPoint();
        IRangeValueSet<ISortedAccumulator<R>> rangeValueSet = rangeTerm.getRangeValueSet();
        computeRangeEndPoint(rangeEndPoint, rangeTerm);
        return checkRange(rangeEndPoint, rangeValueSet, eventValue, rangeTerm.getPropertyName());
    }

    /**
     * This checks the exact in-range value with a marginal value but one can look
     * at the comparison in-range value check using user-defined operator.
     *
     * @param rangeEndPoint
     * @param rangeValueSet
     * @param eventValue
     * @param propertyName
     * @return
     */
    private boolean checkRange(IRangeEndPoint<Double> rangeEndPoint, IRangeValueSet<ISortedAccumulator<R>> rangeValueSet, double eventValue, String propertyName) {
        double startValue = rangeEndPoint.getStartValue();
        double endValue = rangeEndPoint.getEndValue();
        boolean result = true;

        if (startValue <= eventValue && eventValue <= endValue) {
            ISortedAccumulator<R> accumulator = rangeValueSet.getValueSet().getWindow();
            List<R> valueList = accumulator.getMap().firstEntry().getValue();

            for (R value : valueList) {
                Double dataValue = (Double) SchemaUtil.getPropertyValue(value, propertyName);
                modeAggregation.process(new HashMapCounter(), dataValue);
            }
        }
        return (result);
    }

    private void computeRangeEndPoint(IRangeEndPoint<Double> rangeEndPoint, IRangeTerm rangeTerm) {
        IRangeValueSet<ISortedAccumulator<R>> rangeValueSet = rangeTerm.getRangeValueSet();
        ISortedAccumulator<R> accumulator = rangeValueSet.getValueSet().getWindow();
        List<R> valueList = accumulator.getMap().firstEntry().getValue();

        for (R value : valueList) {
            Double dValue = (Double) SchemaUtil.getPropertyValue(value, rangeTerm.getPropertyName());
            minAggregation.process(null, dValue);
            maxAggregation.process(null, dValue);
        }
        rangeEndPoint.setEndValue(maxAggregation.getValue());
        rangeEndPoint.setStartValue(minAggregation.getValue());
    }

    public ModeAggregation getFrequencyCount() {
        return modeAggregation;
    }
}
