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
package org.streameps.filter.eval;

import java.util.List;
import org.streameps.aggregation.collection.AssertionValuePair;
import org.streameps.context.IPredicateTerm;
import org.streameps.core.util.SchemaUtil;
import org.streameps.filter.ContentEvalType;
import org.streameps.filter.FunctorObject;
import org.streameps.filter.FunctorRegistry;
import org.streameps.filter.IEventContentFilterExprn;
import org.streameps.filter.IFunctorObject;
import org.streameps.filter.NumberFunctor;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;

/**
 *
 * @author Frank Appiah
 */
public class ComparisonContentEval<R> implements IEventContentFilterExprn<R> {

    private String functorName;
    private boolean isThresholdFunctor = false;
    private FunctorRegistry registry;

    public ComparisonContentEval() {
        registry = new FunctorRegistry();
    }

    public boolean evalExpr(R eventInstance, IPredicateTerm predicateTerm) {
        Object value, threshold;
        value = SchemaUtil.getPropertyValue(eventInstance, predicateTerm.getPropertyName());

        ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(predicateTerm.getPredicateOperator());
        threshold = predicateTerm.getPropertyValue();
        NumberFunctor functor = (NumberFunctor) registry.getFunctor(getFunctorName());

        boolean result = false;

        if (threshold instanceof Number
                && value instanceof Number) {
            Number num_1 = (Number) value;
            Number num_2 = (Number) threshold;
            if (functor != null) {
                if (isThresholdFunctive()) {
                    IFunctorObject<Number> functorObject = new FunctorObject<Number>();
                    functorObject.getFunctorObjects().add(num_1);
                    functorObject.getFunctorObjects().add(num_2);
                    num_2 = (Number) functor.evalFunction(functorObject);
                }
            }
            if (num_1 instanceof Double || num_2 instanceof Double) {
                result = assertion.assertEvent(new AssertionValuePair(num_2.doubleValue(), num_1.doubleValue()));
            } else if (num_1 instanceof Float
                    || num_2 instanceof Float) {
                result = assertion.assertEvent(new AssertionValuePair(num_2.floatValue(), num_1.floatValue()));
            } else if (num_1 instanceof Integer
                    || num_2 instanceof Integer) {
                result = assertion.assertEvent(new AssertionValuePair(num_2.intValue(), num_1.intValue()));
            } else if (num_1 instanceof Long || num_2 instanceof Long) {
                result = assertion.assertEvent(new AssertionValuePair(num_2.longValue(), num_1.longValue()));
            }
        }

        return result;
    }

    public ContentEvalType getContentEvalType() {
        return ContentEvalType.COMPARISON;
    }

    public String getFunctorName() {
        return this.functorName;
    }

    public boolean isThresholdFunctive() {
        return this.isThresholdFunctor;
    }

    public void enableThresholdFunctor(boolean flag) {
        this.isThresholdFunctor = flag;
    }

    public void setFunctorName(String functorName) {
        this.functorName = functorName;
    }

    public boolean evalExpr(R eventInstance, List<IPredicateTerm> predicateTerms) {
        boolean result = true;
        for (IPredicateTerm iPredicateTerm : predicateTerms) {
            result &= evalExpr(eventInstance, iPredicateTerm);
        }
        return result;
    }
}
