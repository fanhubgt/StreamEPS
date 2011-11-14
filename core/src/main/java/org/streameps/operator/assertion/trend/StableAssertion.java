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
import org.streameps.aggregation.collection.AssertionValuePair;
import org.streameps.core.schema.ISchemaProperty;
import org.streameps.operator.assertion.EqualAssertion;

/**
 * The stable assertion is satisfied if the given attribute has the same value
 * in all the participant events. The stable assertion is satisfied by an
 * attribute A if for all the participant events, e1 << e2 ==> e1.A == e2.A.
 *
 * @author Frank Appiah
 */
public class StableAssertion<E> implements TrendAssertion<E> {

    private Logger logger = Logger.getLogger(StableAssertion.class);

    @Override
    public boolean assessTrend(ITrendObject<E> trendObject) {
        try {
            String attribute = null;
            ISchemaProperty<E> prop1 = trendObject.getTrendList().get(0);
            ISchemaProperty<E> prop2 = trendObject.getTrendList().get(1);
            E e1 = prop1.getEvent(), e2 = prop2.getEvent();
            attribute = trendObject.getAttribute();

            if (prop1.getName().equalsIgnoreCase(attribute)
                    && prop2.getName().equalsIgnoreCase(attribute)) {
                Object val1 = prop1.getAccessorMethod().invoke(e1);
                Object val2 = prop2.getAccessorMethod().invoke(e2);
                if (val1 != null && val2 != null) {
                    Number num_1 = (Number) val1;
                    Number num_2 = (Number) val2;
                    if (num_1 instanceof Double || num_2 instanceof Double) {
                        return new EqualAssertion().assertEvent(new AssertionValuePair(num_1.doubleValue(), num_2.doubleValue()));
                    } else if (num_1 instanceof Float || num_2 instanceof Float) {
                        return new EqualAssertion().assertEvent(new AssertionValuePair(num_1.floatValue(), num_2.floatValue()));
                    } else if (num_1 instanceof Integer
                            || num_2 instanceof Integer) {
                        return new EqualAssertion().assertEvent(new AssertionValuePair(num_1.intValue(),
                                num_2.intValue()));
                    } else if (num_1 instanceof Long || num_2 instanceof Long) {
                        return new EqualAssertion().assertEvent(new AssertionValuePair(
                                num_1.longValue(), num_2.longValue()));
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
        return TrendType.STABLE.toString();
    }
}
