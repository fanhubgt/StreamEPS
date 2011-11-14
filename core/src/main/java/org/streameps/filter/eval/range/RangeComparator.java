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
import org.streameps.aggregation.collection.AssertionValuePair;
import org.streameps.aggregation.collection.ISortedAccumulator;
import org.streameps.core.util.SchemaUtil;
import org.streameps.filter.FilterValueSet;
import org.streameps.filter.IFilterValueSet;
import org.streameps.filter.IInNotValueSet;
import org.streameps.filter.IRangeValueSet;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;

/**
 *
 * @author Frank Appiah
 */
public class RangeComparator<T> implements Comparable<Double> {

    private String propertyName;
    private String operator;
    private T valueSet;

    public RangeComparator(String propertyName, T rangeValueSet) {
        this.propertyName = propertyName;
        this.valueSet = rangeValueSet;
    }

    public RangeComparator(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public int compareTo(Double value) {
        int result = 0;
        Object accumulator = null;
        if (valueSet instanceof IRangeValueSet) {
            IRangeValueSet rangeValueSet = (IRangeValueSet) valueSet;
            accumulator = rangeValueSet.getValueSet().getWindow();
        } else if (valueSet instanceof IFilterValueSet) {
            IFilterValueSet filterValueSet = (FilterValueSet) valueSet;
            accumulator = filterValueSet.getValueSet().getWindow();
        } else if (valueSet instanceof IFilterValueSet) {
            IInNotValueSet inNotValueSet = (IInNotValueSet) valueSet;
            accumulator = inNotValueSet.getValueSet().getWindow();
        }
        if (accumulator instanceof ISortedAccumulator) {
            result = compareValueSet((ISortedAccumulator) accumulator, value);
        }
        return result;
    }

    private int compareValueSet(ISortedAccumulator accumulator, Double value) {

        ISortedAccumulator ac = (ISortedAccumulator) accumulator;
        List<Object> valueList = (List<Object>) ac.getMap().firstEntry().getValue();
        int result = 0;
        for (Object event : valueList) {
            Double dValue = (Double) SchemaUtil.getPropertyValue(event, getPropertyName());
            ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(operator);
            if (assertion.assertEvent(new AssertionValuePair(value, dValue))) {
                result += 1;
            }
        }
        return result;
    }
}
