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

import org.streameps.aggregation.collection.HashMapCounter;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * It aggregates distinct string values from a stream of events.
 * 
 * @author Frank Appiah
 */
public class DistinctAggregation implements IAggregation<HashMapCounter, String> {

    private Logger logger = Logger.getLogger(DistinctAggregation.class);
    private StringBuffer buffer;
    private HashMapCounter mapCounter;
    private String separator = ",";

    public DistinctAggregation() {
        buffer = new StringBuffer("{");
        mapCounter = new HashMapCounter();
    }

    public DistinctAggregation(String separator) {
        buffer = new StringBuffer("{");
        mapCounter = new HashMapCounter();
        this.separator = separator;
    }

    public void process(HashMapCounter cv, String value) {
        cv.incrementAt(value);
        mapCounter = cv;
    }

    public String getValue() {
        Map<Object, Long> map = mapCounter.getMap();
        for (Object key : map.keySet()) {
            buffer.append("[");
            buffer.append(key);
            buffer.append(":");
            buffer.append(map.get(key));
            buffer.append("]");
            buffer.append(separator);
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append("}");
        return buffer.toString();
    }

    public void reset() {
        buffer = new StringBuffer("{");
    }

    public HashMapCounter getBuffer() {
        return this.mapCounter;
    }

    @Override
    public String toString() {
        return "distinct";
    }
}
