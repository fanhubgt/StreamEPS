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
package org.streameps.aggregation;

import org.streameps.aggregation.collection.AggregateIterator;
import org.streameps.aggregation.collection.DoubleAggregateSetValue;

/**
 * @author Development Team
 */
public class MedianAggregation implements Aggregation<DoubleAggregateSetValue,Double> {

    private DoubleAggregateSetValue aggregateValue;
    private AggregateIterator<Double> iterator = null;

    public MedianAggregation() {
        aggregateValue = new DoubleAggregateSetValue();
        iterator = new AggregateIterator(aggregateValue);
    }

    public void process(DoubleAggregateSetValue cv, Double value) {
        cv.add(value);
        aggregateValue = cv;
    }

    public Double getValue() {
        int middle = 0;
        if (aggregateValue.getCount() == middle) {
            return null;
        }
        if (aggregateValue.getCount() == middle + 1) {
            return iterator.getObject(middle);
        }
        middle = aggregateValue.getCount() >> 1;
        if (middle % 2 == 0) {
            return (iterator.getObject(middle - 1) + iterator.getObject(middle)) / 2;
        } else {
            return iterator.getObject(middle);
        }
    }

    @Override
    public String toString() {
        return "median";
    }

    public void reset() {
             aggregateValue = new DoubleAggregateSetValue();
        iterator = new AggregateIterator(aggregateValue);
    }

}
