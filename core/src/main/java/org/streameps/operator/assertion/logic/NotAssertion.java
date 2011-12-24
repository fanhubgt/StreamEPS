/*
 * ====================================================================
 *  StreamEPS Platform
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
 *  =============================================================================
 */
package org.streameps.operator.assertion.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.streameps.aggregation.collection.AssertionValuePair;
import org.streameps.core.util.SchemaUtil;
import org.streameps.operator.assertion.OperatorAssertionFactory;
import org.streameps.operator.assertion.ThresholdAssertion;
import org.streameps.processor.pattern.IPatternParameter;

/**
 * Implementation of a not logical operator assertion.
 * 
 * @author Frank Appiah
 */
public class NotAssertion<L> implements LogicAssertion<L> {

    private Logger logger = Logger.getLogger(NotAssertion.class);

    @Override
    public boolean assertLogic(List<IPatternParameter<L>> params,
            L event) {
        Map<String, Boolean> resultMap = new HashMap<String, Boolean>();
        for (IPatternParameter<L> param : params) {
            Object value = param.getValue();
            ThresholdAssertion assertion = OperatorAssertionFactory.getAssertion(param.getRelation());
            try {
                Object result = SchemaUtil.getPropertyValue(event, param.getPropertyName());
                if (result instanceof String && value instanceof String) {
                    resultMap.put(param.getPropertyName(), ((String) value).equalsIgnoreCase((String) result));
                } else if (result instanceof Number
                        && value instanceof Number) {
                    Number num_1 = (Number) value;
                    Number num_2 = (Number) result;
                    if (num_1 instanceof Double || num_2 instanceof Double) {
                        resultMap.put(param.getPropertyName(),
                                assertion.assertEvent(new AssertionValuePair(
                                num_2.doubleValue(), num_1.doubleValue())));
                    } else if (num_1 instanceof Float
                            || num_2 instanceof Float) {
                        resultMap.put(param.getPropertyName(),
                                assertion.assertEvent(new AssertionValuePair(num_2.floatValue(), num_1.floatValue())));
                    } else if (num_1 instanceof Integer
                            || num_2 instanceof Integer) {
                        resultMap.put(param.getPropertyName(),
                                assertion.assertEvent(new AssertionValuePair(num_2.intValue(), num_1.intValue())));
                    } else if (num_1 instanceof Long || num_2 instanceof Long) {
                        resultMap.put(param.getPropertyName(),
                                assertion.assertEvent(new AssertionValuePair(num_2.longValue(), num_1.longValue())));
                    }
                }
            } catch (IllegalArgumentException e) {
                logger.warn(e);
            }
        }
        boolean negate = true;
        for (String key : resultMap.keySet()) {
            boolean temp = resultMap.get(key);
            negate &= !temp;
        }
        return negate;
    }

    /* (non-Javadoc)
     * @see io.s4.operator.assertion.logic.LogicAssertion#getType()
     */
    @Override
    public LogicType getType() {
        return LogicType.NOT;
    }

    public LogicAssertion<L> getAssertion() {
        return this;
    }
}
