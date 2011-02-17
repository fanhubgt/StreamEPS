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

import org.streameps.aggregation.collection.TreeMapCounter;
import java.util.HashSet;
import java.util.Set;

/**
 * It aggregates the most frequent occurring numeric value from a stream of events
 * via producers, channels or event processing network.
 * 
 * @author Frank Appiah
 */
public class ModeAggregation implements Aggregation<TreeMapCounter, Double> {

    private TreeMapCounter counter = new TreeMapCounter();
    private Set<Double> values = new HashSet<Double>();

    /**
     * It aggregates the value from the stream.
     *
     * @param cv Value aggregate counter
     * @param value Value being aggregated.
     */
    public void process(TreeMapCounter cv, Double value) {
        cv.incrementAt(value);
        counter = cv;
        values.add(value);
    }

    /**
     * It returns the mode of the aggregation.
     * 
     * @return the mode value
     */
    public Double getValue() {
        double mode = 0;
        long freq = 0;
        for (Object v : counter.getMap().keySet()) {
            if (Math.max(counter.totalCountByKey(v), freq) > freq) {
                freq = Math.max(counter.totalCountByKey(v), freq);
                mode = (Double) v;
            }
        }
        return mode;
    }

    /**
     * It resets the aggregated values.
     */
    public void reset() {
        counter = new TreeMapCounter();
        values = new HashSet<Double>();
    }
}
