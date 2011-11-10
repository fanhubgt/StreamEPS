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
package org.streameps.operator.assertion.trend;

import org.apache.log4j.Logger;
import org.streameps.aggregation.AggregateValue;
import org.streameps.core.schema.ISchemaProperty;
import org.streameps.operator.assertion.LessThanOrEqualAssertion;

/**
 * The non-decreasing pattern is satisfied if the value of a given attribute
 * does not decrease within the given context. It assess if e1 << e2 which
 * implies that e1.A <= e2.A where A is a numeric attribute to be compared.
 * 
 * @author  Frank Appiah
 */
public class NonDecreasingAssertion implements TrendAssertion {

    private Logger logger = Logger.getLogger(NonIncreasingAssertion.class);

    @Override
    public boolean assessTrend(ITrendObject trendObject) {
        try {
            String attribute = null;
            ISchemaProperty prop1 = trendObject.getTrendList().get(0);
            ISchemaProperty prop2 = trendObject.getTrendList().get(1);
            Object e1 = prop1.getEvent(), e2 = prop2.getEvent();
            attribute = trendObject.getAttribute();

            if (prop1.getName().equalsIgnoreCase(attribute)
                    && prop2.getName().equalsIgnoreCase(attribute)) {
                Object val1 = prop1.getAccessorMethod().invoke(e1);
                Object val2 = prop2.getAccessorMethod().invoke(e2);
                if (val1 != null && val2 != null) {
                    Number num_1 = (Number) val1;
                    Number num_2 = (Number) val2;
                    if (num_1 instanceof Double || num_2 instanceof Double) {
                        return new LessThanOrEqualAssertion().assertEvent(new AggregateValue(num_2.doubleValue(), num_1.doubleValue()));
                    } else if (num_1 instanceof Float || num_2 instanceof Float) {
                        return new LessThanOrEqualAssertion().assertEvent(new AggregateValue(num_2.floatValue(), num_1.floatValue()));
                    } else if (num_1 instanceof Integer
                            || num_2 instanceof Integer) {
                        return new LessThanOrEqualAssertion().assertEvent(new AggregateValue(num_2.intValue(),
                                num_1.intValue()));
                    } else if (num_1 instanceof Long || num_2 instanceof Long) {
                        return new LessThanOrEqualAssertion().assertEvent(new AggregateValue(
                                num_2.longValue(), num_1.longValue()));
                    }
                }
            }
        } catch (Exception e) {
            logger.warn(e);
        }
        return false;
    }

    @Override
    public String getType() {
        return TrendType.NON_DECREASING.toString();
    }
}
