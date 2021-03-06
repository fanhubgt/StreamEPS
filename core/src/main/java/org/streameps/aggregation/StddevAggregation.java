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

import org.streameps.aggregation.collection.AssertionValuePair;

/**
 * It computes the standard deviation of the aggregate value from an event
 * processing network.
 * 
 * @author Frank Appiah
 */
public class StddevAggregation implements IAggregation<AssertionValuePair,Double> {

    private double sum = 0;
    private double sumSq = 0;
    private long count = 0;
    private AssertionValuePair assertionValuePair;

    public void process(AssertionValuePair cv, Double value) {
        count = cv.threshold++;
        sum = (cv.value += value);
        sumSq += (value * value);
        assertionValuePair=cv;
    }

    public Double getValue() {
        if (count < 2) {
            return null;
        }
        double variance = (sumSq - (sum * sum) / count) / (count - 1);
        return Math.sqrt(variance);
    }

    public void reset() {
        sum = 0;
        sumSq = 0;
        count = 0;
    }

    @Override
    public String toString() {
        return "Standard Deviation";
    }

    public AssertionValuePair getBuffer() {
        return this.assertionValuePair;
    }
}
